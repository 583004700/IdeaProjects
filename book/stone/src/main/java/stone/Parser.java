package stone;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.lang.reflect.Method;
import java.lang.reflect.Constructor;

import stone.ast.ASTree;
import stone.ast.ASTLeaf;
import stone.ast.ASTList;

public class Parser {
    protected static abstract class Element {
        protected abstract void parse(Lexer lexer, List<ASTree> res)
                throws ParseException;

        protected abstract boolean match(Lexer lexer) throws ParseException;
    }

    protected static class Tree extends Element {
        protected Parser parser;

        protected Tree(Parser p) {
            parser = p;
        }

        protected void parse(Lexer lexer, List<ASTree> res)
                throws ParseException {
            res.add(parser.parse(lexer));
        }

        protected boolean match(Lexer lexer) throws ParseException {
            return parser.match(lexer);
        }
    }

    protected static class OrTree extends Element {
        protected Parser[] parsers;

        protected OrTree(Parser[] p) {
            parsers = p;
        }

        protected void parse(Lexer lexer, List<ASTree> res)
                throws ParseException {
            Parser p = choose(lexer);
            if (p == null)
                throw new ParseException(lexer.peek(0));
            else
                res.add(p.parse(lexer));
        }

        protected boolean match(Lexer lexer) throws ParseException {
            return choose(lexer) != null;
        }

        protected Parser choose(Lexer lexer) throws ParseException {
            for (Parser p : parsers)
                if (p.match(lexer))
                    return p;

            return null;
        }

        protected void insert(Parser p) {
            Parser[] newParsers = new Parser[parsers.length + 1];
            newParsers[0] = p;
            System.arraycopy(parsers, 0, newParsers, 1, parsers.length);
            parsers = newParsers;
        }
    }

    protected static class Repeat extends Element {
        protected Parser parser;
        protected boolean onlyOnce;

        protected Repeat(Parser p, boolean once) {
            parser = p;
            onlyOnce = once;
        }

        protected void parse(Lexer lexer, List<ASTree> res)
                throws ParseException {
            while (parser.match(lexer)) {
                ASTree t = parser.parse(lexer);
                if (t.getClass() != ASTList.class || t.numChildren() > 0)
                    res.add(t);
                if (onlyOnce)
                    break;
            }
        }

        protected boolean match(Lexer lexer) throws ParseException {
            return parser.match(lexer);
        }
    }

    protected static abstract class AToken extends Element {
        protected Factory factory;

        protected AToken(Class<? extends ASTLeaf> type) {
            if (type == null)
                type = ASTLeaf.class;
            factory = Factory.get(type, Token.class);
        }

        protected void parse(Lexer lexer, List<ASTree> res)
                throws ParseException {
            Token t = lexer.read();
            if (test(t)) {
                ASTree leaf = factory.make(t);
                res.add(leaf);
            } else
                throw new ParseException(t);
        }

        protected boolean match(Lexer lexer) throws ParseException {
            return test(lexer.peek(0));
        }

        protected abstract boolean test(Token t);
    }

    protected static class IdToken extends AToken {
        HashSet<String> reserved;

        protected IdToken(Class<? extends ASTLeaf> type, HashSet<String> r) {
            super(type);
            reserved = r != null ? r : new HashSet<String>();
        }

        protected boolean test(Token t) {
            return t.isIdentifier() && !reserved.contains(t.getText());
        }
    }

    protected static class NumToken extends AToken {
        protected NumToken(Class<? extends ASTLeaf> type) {
            super(type);
        }

        protected boolean test(Token t) {
            return t.isNumber();
        }
    }

    protected static class StrToken extends AToken {
        protected StrToken(Class<? extends ASTLeaf> type) {
            super(type);
        }

        protected boolean test(Token t) {
            return t.isString();
        }
    }

    protected static class Leaf extends Element {
        protected String[] tokens;

        protected Leaf(String[] pat) {
            tokens = pat;
        }

        protected void parse(Lexer lexer, List<ASTree> res)
                throws ParseException {
            Token t = lexer.read();
            if (t.isIdentifier())
                for (String token : tokens)
                    if (token.equals(t.getText())) {
                        find(res, t);
                        return;
                    }

            if (tokens.length > 0)
                throw new ParseException(tokens[0] + " expected.", t);
            else
                throw new ParseException(t);
        }

        protected void find(List<ASTree> res, Token t) {
            res.add(new ASTLeaf(t));
        }

        protected boolean match(Lexer lexer) throws ParseException {
            Token t = lexer.peek(0);
            if (t.isIdentifier())
                for (String token : tokens)
                    if (token.equals(t.getText()))
                        return true;

            return false;
        }
    }

    protected static class Skip extends Leaf {
        protected Skip(String[] t) {
            super(t);
        }

        protected void find(List<ASTree> res, Token t) {
        }
    }

    public static class Precedence {
        int value;
        boolean leftAssoc; // left associative

        public Precedence(int v, boolean a) {
            value = v;
            leftAssoc = a;
        }
    }

    public static class Operators extends HashMap<String, Precedence> {
        public static boolean LEFT = true;
        public static boolean RIGHT = false;

        public void add(String name, int prec, boolean leftAssoc) {
            put(name, new Precedence(prec, leftAssoc));
        }
    }

    protected static class Expr extends Element {
        protected Factory factory;
        protected Operators ops;
        protected Parser factor;

        protected Expr(Class<? extends ASTree> clazz, Parser exp,
                       Operators map) {
            factory = Factory.getForASTList(clazz);
            ops = map;
            factor = exp;
        }

        public void parse(Lexer lexer, List<ASTree> res) throws ParseException {
            ASTree right = factor.parse(lexer);
            Precedence prec;
            while ((prec = nextOperator(lexer)) != null)
                right = doShift(lexer, right, prec.value);

            res.add(right);
        }

        private ASTree doShift(Lexer lexer, ASTree left, int prec)
                throws ParseException {
            ArrayList<ASTree> list = new ArrayList<ASTree>();
            list.add(left);
            list.add(new ASTLeaf(lexer.read()));
            ASTree right = factor.parse(lexer);
            Precedence next;
            while ((next = nextOperator(lexer)) != null
                    && rightIsExpr(prec, next))
                right = doShift(lexer, right, next.value);

            list.add(right);
            return factory.make(list);
        }

        private Precedence nextOperator(Lexer lexer) throws ParseException {
            Token t = lexer.peek(0);
            if (t.isIdentifier())
                return ops.get(t.getText());
            else
                return null;
        }

        private static boolean rightIsExpr(int prec, Precedence nextPrec) {
            if (nextPrec.leftAssoc)
                return prec < nextPrec.value;
            else
                return prec <= nextPrec.value;
        }

        protected boolean match(Lexer lexer) throws ParseException {
            return factor.match(lexer);
        }
    }

    public static final String factoryName = "create";

    /**
     * 工厂定义了如何创建ASTree
     */
    protected static abstract class Factory {
        protected abstract ASTree make0(Object arg) throws Exception;

        protected ASTree make(Object arg) {
            try {
                return make0(arg);
            } catch (IllegalArgumentException e1) {
                throw e1;
            } catch (Exception e2) {
                throw new RuntimeException(e2); // this compiler is broken.
            }
        }

        /**
         * 通过 clazz，create方法及参数List.class 获取工厂
         * 如果获取工厂为null，则返回将参数强转为 ASTree 的工厂
         *
         * @param clazz
         * @return
         */
        protected static Factory getForASTList(Class<? extends ASTree> clazz) {
            Factory f = get(clazz, List.class);
            if (f == null)
                f = new Factory() {
                    protected ASTree make0(Object arg) throws Exception {
                        List<ASTree> results = (List<ASTree>) arg;
                        if (results.size() == 1)
                            return results.get(0);
                        else
                            return new ASTList(results);
                    }
                };
            return f;
        }

        /**
         * 获取 clazz 的 create方法，返回的工厂为创建 create方法返回值的工厂
         * 或者获取 clazz 的 构造方法，并返回的工厂为 clazz 构造方法返回值的工厂
         *
         * @param clazz
         * @param argType
         * @return
         */
        protected static Factory get(Class<? extends ASTree> clazz,
                                     Class<?> argType) {
            if (clazz == null)
                return null;
            try {
                final Method m = clazz.getMethod(factoryName,
                        new Class<?>[]{argType});
                return new Factory() {
                    protected ASTree make0(Object arg) throws Exception {
                        return (ASTree) m.invoke(null, arg);
                    }
                };
            } catch (NoSuchMethodException e) {
            }
            try {
                final Constructor<? extends ASTree> c
                        = clazz.getConstructor(argType);
                return new Factory() {
                    protected ASTree make0(Object arg) throws Exception {
                        return c.newInstance(arg);
                    }
                };
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected List<Element> elements;
    protected Factory factory;

    public Parser(Class<? extends ASTree> clazz) {
        reset(clazz);
    }

    protected Parser(Parser p) {
        elements = p.elements;
        factory = p.factory;
    }

    /**
     * 执行语法分析
     *
     * @param lexer
     * @return
     * @throws ParseException
     */
    public ASTree parse(Lexer lexer) throws ParseException {
        ArrayList<ASTree> results = new ArrayList<ASTree>();
        for (Element e : elements)
            e.parse(lexer, results);

        return factory.make(results);
    }

    protected boolean match(Lexer lexer) throws ParseException {
        if (elements.size() == 0)
            return true;
        else {
            Element e = elements.get(0);
            return e.match(lexer);
        }
    }

    /**
     * 创建Parser对象
     *
     * @return
     */
    public static Parser rule() {
        return rule(null);
    }

    /**
     * 创建Parser对象
     *
     * @param clazz
     * @return
     */
    public static Parser rule(Class<? extends ASTree> clazz) {
        return new Parser(clazz);
    }

    /**
     * 清空语法规则
     *
     * @return
     */
    public Parser reset() {
        elements = new ArrayList<Element>();
        return this;
    }

    /**
     * 清空语法规则，将节点类赋值为clazz
     *
     * @param clazz
     * @return
     */
    public Parser reset(Class<? extends ASTree> clazz) {
        elements = new ArrayList<Element>();
        factory = Factory.getForASTList(clazz);
        return this;
    }

    /**
     * 向语法规则中添加终结符（整型字面量）
     *
     * @return
     */
    public Parser number() {
        return number(null);
    }

    /**
     * 向语法规则中添加终结符（整型字面量）
     *
     * @param clazz
     * @return
     */
    public Parser number(Class<? extends ASTLeaf> clazz) {
        elements.add(new NumToken(clazz));
        return this;
    }

    /**
     * 向语法规则中添加终结符（除保留字reserved外的标识符）
     *
     * @param reserved
     * @return
     */
    public Parser identifier(HashSet<String> reserved) {
        return identifier(null, reserved);
    }

    /**
     * 向语法规则中添加终结符（除保留字reserved外的标识符）
     *
     * @param clazz
     * @param reserved
     * @return
     */
    public Parser identifier(Class<? extends ASTLeaf> clazz,
                             HashSet<String> reserved) {
        elements.add(new IdToken(clazz, reserved));
        return this;
    }

    /**
     * 向语法规则中添加终结符（字符串字面量）
     *
     * @return
     */
    public Parser string() {
        return string(null);
    }

    /**
     * 向语法规则中添加终结符（字符串字面量）
     *
     * @param clazz
     * @return
     */
    public Parser string(Class<? extends ASTLeaf> clazz) {
        elements.add(new StrToken(clazz));
        return this;
    }

    /**
     * 向语法规则中添加终结符（与pat匹配的标识符）
     *
     * @param pat
     * @return
     */
    public Parser token(String... pat) {
        elements.add(new Leaf(pat));
        return this;
    }

    /**
     * 向语法规则中添加未包含于抽象语法树的终结符（与pat匹配的标识符）
     *
     * @param pat
     * @return
     */
    public Parser sep(String... pat) {
        elements.add(new Skip(pat));
        return this;
    }

    /**
     * 向语法规则中添加非终结符p
     *
     * @param p
     * @return
     */
    public Parser ast(Parser p) {
        elements.add(new Tree(p));
        return this;
    }

    /**
     * 向语法规则中添加若干个由or关系连接的非终结符p
     *
     * @param p
     * @return
     */
    public Parser or(Parser... p) {
        elements.add(new OrTree(p));
        return this;
    }

    /**
     * 向语法规则中添加可省略的非终结符p（如果省略，则作为一颗仅有根节点的抽象语法树处理）
     *
     * @param p
     * @return
     */
    public Parser maybe(Parser p) {
        Parser p2 = new Parser(p);
        p2.reset();
        elements.add(new OrTree(new Parser[]{p, p2}));
        return this;
    }

    /**
     * 向语法规则中添加可省略的非终结符p
     *
     * @param p
     * @return
     */
    public Parser option(Parser p) {
        elements.add(new Repeat(p, true));
        return this;
    }

    /**
     * 向语法规则中添加至少重复出现0次的非终结符p
     *
     * @param p
     * @return
     */
    public Parser repeat(Parser p) {
        elements.add(new Repeat(p, false));
        return this;
    }

    /**
     * 向语法规则中添加双目运算表达式（subexp是因子，operators是运算符表）
     *
     * @param subexp
     * @param operators
     * @return
     */
    public Parser expression(Parser subexp, Operators operators) {
        elements.add(new Expr(null, subexp, operators));
        return this;
    }

    /**
     * 向语法规则中添加双目运算表达式（subexp是因子，operators是运算符表）
     *
     * @param clazz
     * @param subexp
     * @param operators
     * @return
     */
    public Parser expression(Class<? extends ASTree> clazz, Parser subexp,
                             Operators operators) {
        elements.add(new Expr(clazz, subexp, operators));
        return this;
    }

    /**
     * 为语法规则起始处的or添加新的分支选项
     *
     * @param p
     * @return
     */
    public Parser insertChoice(Parser p) {
        Element e = elements.get(0);
        if (e instanceof OrTree)
            ((OrTree) e).insert(p);
        else {
            Parser otherwise = new Parser(this);
            reset(null);
            or(p, otherwise);
        }
        return this;
    }
}
