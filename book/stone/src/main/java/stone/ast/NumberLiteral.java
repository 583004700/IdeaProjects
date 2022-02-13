package stone.ast;
import stone.Token;

/**
 * 整型字面量
 */
public class NumberLiteral extends ASTLeaf {
    public NumberLiteral(Token t) { super(t); }
    public int value() { return token().getNumber(); }
}
