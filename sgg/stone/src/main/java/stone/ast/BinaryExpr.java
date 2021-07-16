package stone.ast;
import java.util.List;

/**
 * 二元运算符，具有两个操作数
 */
public class BinaryExpr extends ASTList {
    public BinaryExpr(List<ASTree> c) { super(c); }
    public ASTree left() { return child(0); }
    public String operator() {
        return ((ASTLeaf)child(1)).token().getText();
    }
    public ASTree right() { return child(2); }
}
