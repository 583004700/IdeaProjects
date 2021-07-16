package stone.ast;
import java.util.Iterator;

public abstract class ASTree implements Iterable<ASTree> {

    /**
     * 获取第i个子节点
     * @param i
     * @return
     */
    public abstract ASTree child(int i);

    /**
     * 获取子节点数量
     * @return
     */
    public abstract int numChildren();

    /**
     * 得到子节点迭代器
     * @return
     */
    public abstract Iterator<ASTree> children();

    /**
     * 定位行号信息
     * @return
     */
    public abstract String location();

    /**
     * 本身也是可迭代的
     * @return
     */
    public Iterator<ASTree> iterator() { return children(); }
}
