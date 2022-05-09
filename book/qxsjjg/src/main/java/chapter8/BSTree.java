package chapter8;

import lombok.Getter;
import lombok.Setter;

/**
 * 二叉查找树
 */
public class BSTree<T extends Comparable> {

    @Setter
    @Getter
    public static class Node<T extends Comparable>{
        private T data;
        private Node<T> left;
        private Node<T> right;
    }

    private Node<T> root;

    /**
     * 书上描述不是很清楚
     * 如果被删除的节点没有左右孩子，则直接解除被删除节点与父节点的关系
     * 如果被删除的节点有孩子，则删除左子树最大值，或删除右子树最小值。并将被删除节点的数据域替换为左子树最大值，或右子树最小值。
     * 有左右孩子时，任选其一即可，如果只有左孩子，则删除左子树最大值。如果只有右孩子，则删除右孩子最小值。并将被删除节点的数据域替换为子树最大或最小值。
     * @param data
     */
    public void delete(T data){
        Node<T> target = search(data);
        if(target != null) {
            Node<T> parent = searchParent(target.getData());
            if (target.getLeft() == null && target.getRight() == null) {
                if(data.equals(parent.getLeft().getData())){
                    parent.setLeft(null);
                }else if(data.equals(parent.getRight().getData())){
                    parent.setRight(null);
                }
            }else if(target.getLeft() != null){
                Node<T> leftMax = doSearchMax(target.getLeft());
                T leftMaxData = leftMax.getData();
                delete(leftMaxData);
                target.setData(leftMaxData);
            }else{
                Node<T> rightMin = doSearchMin(target.getRight());
                T rightMinData = rightMin.getData();
                delete(rightMinData);
                target.setData(rightMinData);
            }
        }
    }

    public Node<T> searchParent(T data){
        Node<T> t = new Node<T>();
        t.setData(data);
        return doSearchParent(root,t);
    }

    /**
     * 查找父节点
     * @param start 从哪个节点开始
     * @param node  要查找的节点
     * @return
     */
    public Node<T> doSearchParent(Node<T> start,Node<T> node){
        if(start == null || node == null || node.getData() == null){
            return null;
        }
        Node<T> result = null;
        if(start.getLeft() != null){
            if(node.getData().equals(start.getLeft().getData())){
                return start;
            }
            result = doSearchParent(start.getLeft(),node);
            if(result != null){
                return result;
            }
        }
        if(start.getRight() != null){
            if(node.getData().equals(start.getRight().getData())){
                return start;
            }
            return doSearchParent(start.getRight(),node);
        }
        return null;
    }

    /**
     * 查找最小的节点
     * @param node
     * @return
     */
    public Node<T> doSearchMin(Node<T> node){
        if(node == null){
            return null;
        }
        Node<T> t = node;
        while(t.getLeft() != null){
            t = t.getLeft();
        }
        return t;
    }

    /**
     * 查找最大的节点
     * @param node
     * @return
     */
    public Node<T> doSearchMax(Node<T> node){
        if(node == null){
            return null;
        }
        Node<T> t = node;
        while(t.getRight() != null){
            t = t.getRight();
        }
        return t;
    }

    public Node<T> search(T data){
        return doSearch(root,data);
    }

    public Node<T> doSearch(Node<T> node,T data){
        if(node == null){
            return null;
        }
        if(data.compareTo(node.getData()) < 0){
            if(node.getLeft() != null){
                return doSearch(node.getLeft(),data);
            }else{
                return null;
            }
        }else if(data.compareTo(node.getData()) > 0){
            if(node.getRight() != null){
                return doSearch(node.getRight(),data);
            }else{
                return null;
            }
        }else{
            return node;
        }
    }

    public void add(T data){
        doAdd(root,data);
    }

    private void doAdd(Node<T> node,T data){
        if(root == null){
            Node<T> temp = new Node<T>();
            temp.setData(data);
            this.root = temp;
        }else{
            if(data.compareTo(node.getData()) < 0){
                if(node.getLeft() != null){
                    doAdd(node.getLeft(),data);
                }else{
                    Node<T> temp = new Node<T>();
                    temp.setData(data);
                    node.setLeft(temp);
                }
            }else{
                if(node.getRight() != null){
                    doAdd(node.getRight(),data);
                }else{
                    Node<T> temp = new Node<T>();
                    temp.setData(data);
                    node.setRight(temp);
                }
            }
        }
    }

    public static void main(String[] args) {
        BSTree<Integer> bsTree = new BSTree<Integer>();
        bsTree.add(2);
        bsTree.add(4);
        bsTree.add(3);
        bsTree.add(8);
        bsTree.add(1);
        bsTree.add(9);
        bsTree.add(7);
        bsTree.add(5);
        bsTree.add(6);
        System.out.println(bsTree);
        System.out.println(bsTree.search(3).getData());
        System.out.println(bsTree.search(1).getData());
        System.out.println(bsTree.search(5));
        bsTree.delete(4);
        bsTree.delete(2);
        System.out.println(bsTree);
    }

}
