package chapter6.datastructure.linebinarytree;

public class Test {
    public static void main(String[] args) {
        Node<String> a = new Node<String>();
        a.setData("A");
        Node<String> b = new Node<String>();
        b.setData("B");
        Node<String> c = new Node<String>();
        c.setData("C");
        Node<String> d = new Node<String>();
        d.setData("D");
        Node<String> e = new Node<String>();
        e.setData("E");
        Node<String> f = new Node<String>();
        f.setData("F");
        Node<String> g = new Node<String>();
        g.setData("G");

        a.setLeft(b);
        a.setRight(c);
        b.setLeft(d);
        b.setRight(e);
        c.setLeft(f);
        f.setRight(g);

        LineBinaryTree<String> lineBinaryTree = new LineBinaryTree<String>(a);
        //System.out.println(lineBinaryTree);
        //lineBinaryTree.lineErgodic();
        lineBinaryTree.preErgodic();
        int depth = lineBinaryTree.depth();
        System.out.println("树的深度为！"+depth);
        int leafNum = lineBinaryTree.leafNum();
        System.out.println("树的叶子数为！"+leafNum);
        int nodeNum = lineBinaryTree.nodeNum();
        System.out.println("树的节点数为！"+nodeNum);
        LineBinaryTree<String> tree = LineBinaryTree.revivification("ABDECFG", "DBEAFGC");
        System.out.println("---------------------------");
    }
}
