package chapter6.datastructure.linebinarytree;

import lombok.Getter;
import lombok.Setter;

/**
 * 线索二叉树（构造时根据前序，中序，后序的不同，最终结构也是不同的）
 */
@Setter
@Getter
public class LineBinaryTree<T> {
    private Node<T> root;
    private Node<T> pre;
    private static int rootIndex;

    /**
     * 根据先序和后序 序列还原二叉树
     * @param front
     * @param middle
     * @return
     */
    public static LineBinaryTree<String> revivification(String front, String middle) {
        rootIndex = 0;
        return doRevivification(front, middle);
    }

    private static LineBinaryTree<String> doRevivification(String front, String middle) {
        if (middle.length() == 1) {
            rootIndex++;
            LineBinaryTree<String> l = new LineBinaryTree<String>();
            Node<String> r = new Node<String>();
            r.setData(middle);
            l.setRoot(r);
            return l;
        }

        if (middle.length() == 0) {
            rootIndex++;
            return null;
        }

        String root = String.valueOf(front.charAt(rootIndex));
        Node<String> rootNode = new Node<String>();
        rootNode.setData(root);
        String left = middle.substring(0, middle.indexOf(root));
        String right = middle.substring(middle.indexOf(root) + 1);
        rootIndex++;
        LineBinaryTree<String> leftTree = doRevivification(front, left);
        LineBinaryTree<String> rightTree = doRevivification(front, right);
        if (leftTree != null) {
            rootNode.setLeft(leftTree.getRoot());
        }
        if (rightTree != null) {
            rootNode.setRight(rightTree.getRoot());
        }

        LineBinaryTree<String> l = new LineBinaryTree<String>();
        l.setRoot(rootNode);
        return l;
    }

    public LineBinaryTree() {

    }

    public LineBinaryTree(Node<T> root) {
        this.root = root;
        //line(root);
    }

    public int nodeNum() {
        return doNodeNum(root);
    }

    private int doNodeNum(Node<T> node) {
        if (node == null) {
            return 0;
        }
        int leftNodeNum = doNodeNum(node.getLeft());
        int rightNodeNum = doNodeNum(node.getRight());
        return leftNodeNum + rightNodeNum + 1;
    }

    public int leafNum() {
        return doLeafNum(root);
    }

    private int doLeafNum(Node<T> node) {
        if (node == null) {
            return 0;
        }
        if (node.getLeft() == null && node.getRight() == null) {
            return 1;
        }
        int leftLeafNum = doLeafNum(node.getLeft());
        int rightLeafNum = doLeafNum(node.getRight());
        return leftLeafNum + rightLeafNum;
    }

    public int depth() {
        return doDepth(root);
    }

    private int doDepth(Node<T> node) {
        if (node == null) {
            return 0;
        }
        int leftLength = doDepth(node.getLeft());
        int rightLength = doDepth(node.getRight());
        return Math.max(leftLength, rightLength) + 1;
    }

    /**
     * 树的先序遍历
     */
    public void preErgodic() {
        System.out.println("树的先序遍历！");
        doPreErgodic(root);
    }

    /**
     * 树的先序遍历
     */
    private void doPreErgodic(Node<T> node) {
        System.out.println(node.getData());
        if (node.getLeft() != null) {
            doPreErgodic(node.getLeft());
        }
        if (node.getRight() != null) {
            doPreErgodic(node.getRight());
        }
    }

    /**
     * 线索化(中序)
     */
    private void line(Node<T> node) {
        if (node.getLeft() != null) {
            line(node.getLeft());
        }
        if (pre != null) {
            if (node.getLeft() == null) {
                node.setFront(pre);
            }
            if (pre.getRight() == null) {
                pre.setNext(node);
            }
        }
        pre = node;
        if (node.getRight() != null) {
            line(node.getRight());
        }
    }

    /**
     * 线索遍历(中序)
     */
    public void lineErgodic() {
        System.out.println("线索中序遍历！");
        doErgodic(root);
    }

    private void doErgodic(Node<T> node) {
        Node<T> p = node;
        while (p.getLeft() != null && p.getLeftType() == 0) {
            p = p.getLeft();
        }
        int type = 0;
        do {
            System.out.println(p.getData());
            type = p.getRightType();
            p = p.getRight();
        } while (type == 1);
        if (p != null) {
            doErgodic(p);
        }
    }
}
