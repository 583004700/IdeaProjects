package stone;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    // 可以有空白字符，注释，数字，字符串（双引号之间内容可以是,空,\",\\,\n,不能是,"），字母或下划线开头可以包含数字的变量名，==，<=，>=，&&，||，标点符号
    public static String regexPat
        = "\\s*((//.*)|([0-9]+)|(\"(\\\\\"|\\\\\\\\|\\\\n|[^\"])*\")"
          + "|[A-Z_a-z][A-Z_a-z0-9]*|==|<=|>=|&&|\\|\\||\\p{Punct})?";
    private Pattern pattern = Pattern.compile(regexPat);
    private ArrayList<Token> queue = new ArrayList<Token>();
    private boolean hasMore;
    private LineNumberReader reader;

    public Lexer(Reader r) {
        hasMore = true;
        reader = new LineNumberReader(r);
    }
    public Token read() throws ParseException {
        if (fillQueue(0))
            return queue.remove(0);
        else
            return Token.EOF;
    }

    /**
     * 读取指定下标的token，如果下标比队列长度大。则填充后再读取。如果没有，则返回 Token.EOF
     * @param i
     * @return
     * @throws ParseException
     */
    public Token peek(int i) throws ParseException {
        if (fillQueue(i))
            return queue.get(i);
        else
            return Token.EOF; 
    }

    /**
     * 如果队列的长度小于i，则再读取一行数据。直到队列长度大于i。如果一直读取最终还是小于i，则返回false
     * @param i
     * @return
     * @throws ParseException
     */
    private boolean fillQueue(int i) throws ParseException {
        while (i >= queue.size())
            if (hasMore)
                readLine();
            else
                return false;
        return true;
    }

    /**
     * 读取一行数据，并添加扫描到的token到队列中，包括换行的token，如果没有数据，则hasMore=false
     * @throws ParseException
     */
    protected void readLine() throws ParseException {
        String line;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            throw new ParseException(e);
        }
        if (line == null) {
            hasMore = false;
            return;
        }
        int lineNo = reader.getLineNumber();
        Matcher matcher = pattern.matcher(line);
        matcher.useTransparentBounds(true).useAnchoringBounds(false);
        int pos = 0;
        int endPos = line.length();
        while (pos < endPos) {
            matcher.region(pos, endPos);
            if (matcher.lookingAt()) {
                addToken(lineNo, matcher);
                pos = matcher.end();
            }
            else
                throw new ParseException("bad token at line " + lineNo);
        }
        queue.add(new IdToken(lineNo, Token.EOL));
    }

    /**
     * 判断 token的类型，并创建token添加到队列中
     * @param lineNo
     * @param matcher
     */
    protected void addToken(int lineNo, Matcher matcher) {
        String m = matcher.group(1);
        if (m != null) // if not a space
            if (matcher.group(2) == null) { // if not a comment
                Token token;
                if (matcher.group(3) != null)
                    // 整数值
                    token = new NumToken(lineNo, Integer.parseInt(m));
                else if (matcher.group(4) != null)
                    // 字符串
                    token = new StrToken(lineNo, toStringLiteral(m));
                else
                    // 标识符
                    token = new IdToken(lineNo, m);
                queue.add(token);
            }
    }

    /**
     * 判断字符串中间（第一个字符和最后一个字符之间）是否包含 \" \\ \n 等转义字符，如果有，则添加 " 或 \ 或 换行符（\n）到sb中并返回
     * 处理转义字符
     * @param s
     * @return
     */
    protected String toStringLiteral(String s) {
        StringBuilder sb = new StringBuilder();
        int len = s.length() - 1;
        for (int i = 1; i < len; i++) {
            char c = s.charAt(i);
            if (c == '\\' && i + 1 < len) {
                int c2 = s.charAt(i + 1);
                if (c2 == '"' || c2 == '\\')
                    c = s.charAt(++i);
                else if (c2 == 'n') {
                    ++i;
                    c = '\n';
                }
            }
            sb.append(c);
        }
        return sb.toString();
    }

    protected static class NumToken extends Token {
        private int value;

        protected NumToken(int line, int v) {
            super(line);
            value = v;
        }
        public boolean isNumber() { return true; }
        public String getText() { return Integer.toString(value); }
        public int getNumber() { return value; }
    }

    protected static class IdToken extends Token {
        private String text; 
        protected IdToken(int line, String id) {
            super(line);
            text = id;
        }
        public boolean isIdentifier() { return true; }
        public String getText() { return text; }
    }

    protected static class StrToken extends Token {
        private String literal;
        StrToken(int line, String str) {
            super(line);
            literal = str;
        }
        public boolean isString() { return true; }
        public String getText() { return literal; }
    }
}
