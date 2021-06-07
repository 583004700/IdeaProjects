package mysources;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Tree {
    //指向第一个节点
    public TreeNode first;
    //指向当前要给哪个节点添加子节点
    public TreeNode current;
    //当前被添加节点的上一个临时节点
    public TreeNode preNode;

    //线索化的前一个临时节点
    public TreeNode threadedPreNode;

    public void add(TreeNode treeNode){
        if(first == null){
            first = treeNode;
            current = treeNode;
            //第一个节点的父节点的value为null
            treeNode.parent = new TreeNode(null);
            preNode = treeNode;
            return;
        }
        if(current.left != null && current.right != null){
           current = current.next;
        }
        if(current.left == null){
            current.left = treeNode;
            treeNode.parent = current;
        }else if(current.right == null){
            current.right = treeNode;
            treeNode.parent = current;
        }
        preNode.next = treeNode;
        preNode = treeNode;
    }

    /**
     * 按顺序遍历树(非前序中序后序。只是按从上到下从左到右)
     */

    public static List<TreeNode> treeNodes = new ArrayList<TreeNode>();

    public static void fore(TreeNode treeNode){

        LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
        queue.add(treeNode);

        //被移除的数据
        TreeNode r = null;
        while((r = queue.poll()) != null){    //队列中还有数据
            //添加到集合中
            treeNodes.add(r);
            if(r.left != null){
                queue.add(r.left);
            }
            if(r.right != null){
                queue.add(r.right);
            }
        }
        System.out.println(treeNodes);
    }

    /**
     * 前序遍历
     * @param treeNode
     */
    public static void preOrder(TreeNode treeNode){
        System.out.print(treeNode.value+"  ");
        if(treeNode.left != null){
            preOrder(treeNode.left);
        }
        if(treeNode.right != null){
            preOrder(treeNode.right);
        }
    }

    /**
     * 后序遍历
     * @param treeNode
     */
    public static void lastOrder(TreeNode treeNode){
        if(treeNode.left != null){
            lastOrder(treeNode.left);
        }
        if(treeNode.right != null){
            lastOrder(treeNode.right);
        }
        System.out.print(treeNode.value+"  ");
    }

    /**
     * 中序遍历
     * @param treeNode
     */
    public static void midOrder(TreeNode treeNode){
        if(treeNode.left != null){
            midOrder(treeNode.left);
        }
        System.out.print(treeNode.value+"  ");
        if(treeNode.right != null){
            midOrder(treeNode.right);
        }
    }

    /**
     * 按后序顺序线索化
     */
    public static void lastThreadedNodes(TreeNode treeNode){
        if(treeNode.left != null){
            lastThreadedNodes(treeNode.left);
        }
        if(treeNode.right != null){
            lastThreadedNodes(treeNode.right);
        }
        if(treeNode.left == null){
            //代表是指向的是前一个节点,不是左子树
            treeNode.threadedLeftType = 1;
            //取出前一个节点并赋值给left
            treeNode.left = tree.threadedPreNode;
        }
        if(tree.threadedPreNode != null && tree.threadedPreNode.right == null){
            tree.threadedPreNode.threadedRightType = 1;
            tree.threadedPreNode.right = treeNode;
        }
        tree.threadedPreNode = treeNode;
    }

    public static Tree tree = new Tree();

    public static void main(String[] args) {

        /**
         * 按从上至下，从左至右顺序创建树
         */
        for(int i=1;i<=10;i++){
            TreeNode treeNode = new TreeNode(i);
            tree.add(treeNode);
        }

        System.out.println(tree);
        //从头节点开始遍历
        fore(tree.first);
        //前序遍历
        preOrder(tree.first);
        //中序遍历
        System.out.println();
        midOrder(tree.first);
        System.out.println();

        System.out.println("后序遍历结果====");
        //后序遍历
        lastOrder(tree.first);
        //后序线索化
        lastThreadedNodes(tree.first);

        System.out.println();
        System.out.println("后序线索化的结果===");
        for(int i=0;i<treeNodes.size();i++){
            if(treeNodes.get(i).threadedLeftType == 1){
                System.out.println(treeNodes.get(i).value+"的前一个为"+treeNodes.get(i).left);
            }
            if(treeNodes.get(i).threadedRightType == 1){
                System.out.println(treeNodes.get(i).value+"的后一个为"+treeNodes.get(i).right);
            }
        }
    }
}

class TreeNode{
    public Integer value;
    public TreeNode left;
    public TreeNode right;

    public int threadedLeftType;//0为左子树，1为前节点
    public int threadedRightType;//0为右子树，1为后节点

    public TreeNode parent;
    //指向下一个节点，按添加顺序
    public TreeNode next;

    public TreeNode(Integer value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
}
