package chapter8;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 平衡二叉查找树
 */
public class AVLTree<T extends Comparable<T>> {

    @Setter
    @Getter
    public static class Node<T extends Comparable<T>>{
        private T data;
        private Node<T> left;
        private Node<T> right;

        @Override
        public String toString() {
            return data.toString();
        }
    }

    private Node<T> root;


    public void printTree() {
        if(root == null){
            return;
        }
        Map<Integer, List<Node<T>>> map = new HashMap<Integer, List<Node<T>>>();
        List<Node<T>> first = new ArrayList<Node<T>>();
        first.add(root);
        map.put(1,first);
        int level = 1;
        while(map.containsKey(level)){
            List<Node<T>> nodes = map.get(level);
            Integer nextKey = level+1;
            for (Node<T> node : nodes) {
                if(!map.containsKey(nextKey) && (node.getLeft()!=null || node.getRight() != null)){
                    map.put(nextKey,new ArrayList<Node<T>>());
                }
                List<Node<T>> nextNodes = map.get(nextKey);
                if(node.getLeft() != null) {
                    nextNodes.add(node.getLeft());
                }
                if(node.getRight() != null) {
                    nextNodes.add(node.getRight());
                }
            }
            level++;
        }

        level = 1;
        while(map.containsKey(level)){
            List<Node<T>> nodes = map.get(level);
            for (Node<T> node : nodes) {
                System.out.print(node+"父:"+searchParent(node.getData())+" ");
            }
            System.out.println();
            level++;
        }
    }

    /**
     * 书上描述不是很清楚
     * 如果被删除的节点没有左右孩子，则直接解除被删除节点与父节点的关系
     * 如果被删除的节点有孩子，则删除左子树最大值，或删除右子树最小值。并将被删除节点的数据域替换为左子树最大值，或右子树最小值。
     * 有左右孩子时，任选其一即可，如果只有左孩子，则删除左子树最大值。如果只有右孩子，则删除右孩子最小值。并将被删除节点的数据域替换为子树最大或最小值。
     * @param data
     */
    public void delete(T data){
        Node<T> target = search(data);
        Node<T> route = null;
        if(target != null) {
            Node<T> parent = searchParent(target.getData());
            if (target.getLeft() == null && target.getRight() == null) {
                if(target == root){
                    root = null;
                    return;
                }
                if(parent.getLeft() != null && data.equals(parent.getLeft().getData())){
                    parent.setLeft(null);
                }else if(parent.getRight() != null && data.equals(parent.getRight().getData())){
                    parent.setRight(null);
                }
                route = parent;
                while(route != null){
                    Balance<T> balance = isBalance(route);
                    if(balance != null){
                        balance(balance);
                    }
                    route = searchParent(route.getData());
                }
            }else if(target.getLeft() != null){
                Node<T> leftMax = doSearchMax(target.getLeft());
                delete(leftMax.getData());
                target.setData(leftMax.getData());
            }else{
                Node<T> rightMin = doSearchMin(target.getRight());
                delete(rightMin.getData());
                target.setData(rightMin.getData());
            }
        }
    }

    public Node<T> LL(Node<T> node){
        Node<T> left = node.getLeft();
        Node<T> leftRight = left.getRight();
        left.setRight(node);
        node.setLeft(leftRight);
        Node<T> parent = searchParent(node.getData());
        if(parent != null) {
            if (node.equals(parent.getLeft())) {
                parent.setLeft(left);
            } else if (node.equals(parent.getRight())) {
                parent.setRight(left);
            }
        }else{
            root = left;
        }
        return left;
    }

    public Node<T> RR(Node<T> node){
        Node<T> right = node.getRight();
        Node<T> rightLeft = right.getLeft();
        right.setLeft(node);
        node.setRight(rightLeft);
        Node<T> parent = searchParent(node.getData());
        if(parent != null) {
            if (node.equals(parent.getLeft())) {
                parent.setLeft(right);
            } else if (node.equals(parent.getRight())) {
                parent.setRight(right);
            }
        }else{
            root = right;
        }
        return right;
    }

    public Node<T> searchParent(T data){
        Node<T> t = new Node<T>();
        t.setData(data);
        return doSearchParent(root,t);
    }

    public int doHeight(Node<T> node){
        if(node == null){
            return 0;
        }
        if(node.getLeft() == null && node.getRight() == null){
            return 1;
        }
        int leftHeight = doHeight(node.getLeft());
        int rightHeight = doHeight(node.getRight());
        int max = Math.max(leftHeight, rightHeight);
        return max+1;
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
            Node<T> temp = null;
            if(data.compareTo(node.getData()) < 0){
                if(node.getLeft() != null){
                    doAdd(node.getLeft(),data);
                }else{
                    temp = new Node<T>();
                    temp.setData(data);
                    node.setLeft(temp);
                }
            }else if(data.compareTo(node.getData()) > 0){
                if(node.getRight() != null){
                    doAdd(node.getRight(),data);
                }else{
                    temp = new Node<T>();
                    temp.setData(data);
                    node.setRight(temp);
                }
            }
            if(temp != null) {
                Node<T> parent = null;
                while ((parent = searchParent(temp.getData())) != null) {
                    int leftHeight = doHeight(parent.getLeft());
                    int rightHeight = doHeight(parent.getRight());
                    if (Math.abs(leftHeight - rightHeight) > 1) {
                        break;
                    }
                    temp = parent;
                }
                if (parent != null) {
                    Balance<T> balance = isBalance(temp);
                    balance(balance);
                }
            }
        }
    }

    /**
     * 调整为平衡
     * @param balance
     */
    public void balance(Balance<T> balance){
        String type = balance.getType();
        Node<T> parent = balance.getNode();
        if("LL".equals(type)){
            LL(parent);
        }else if("RR".equals(type)){
            RR(parent);
        }else if("LR".equals(type)){
            RR(parent.getLeft());
            LL(parent);
        }else if("RL".equals(type)){
            LL(parent.getRight());
            RR(parent);
        }
    }

    /**
     * 判断是否平衡，从子节点向上查找
     * @param temp
     * @return
     */
    public Balance<T> isBalance(Node<T> temp){
        if(temp != null) {
            String first = null;
            String second = null;
            Node<T> current = temp;
            while (current != null) {
                int leftHeight = doHeight(current.getLeft());
                int rightHeight = doHeight(current.getRight());
                if (Math.abs(leftHeight - rightHeight) > 1) {
                    break;
                }
                current = searchParent(current.getData());
            }
            if (current != null) {
                int leftHeight = doHeight(current.getLeft());
                int rightHeight = doHeight(current.getRight());
                if (leftHeight < rightHeight) {
                    first = "R";
                    int secondLeftHeight = doHeight(current.getRight().getLeft());
                    int secondRightHeight = doHeight(current.getRight().getRight());
                    if (secondLeftHeight < secondRightHeight) {
                        second = "R";
                    } else {
                        second = "L";
                    }
                } else {
                    first = "L";
                    int secondLeftHeight = doHeight(current.getLeft().getLeft());
                    int secondRightHeight = doHeight(current.getLeft().getRight());
                    if (secondLeftHeight < secondRightHeight) {
                        second = "R";
                    } else {
                        second = "L";
                    }
                }
                String type = first+second;
                System.out.println("不平衡点为：" + current.getData() + ",类型为：" + type);
                Balance<T> balance = new Balance<T>();
                balance.setType(type);
                balance.setNode(current);
                return balance;
            }
        }
        return null;
    }

    @Setter
    @Getter
    public static class Balance<T extends Comparable<T>>{
        // LL,RR,LR,RL
        private String type;
        // 不平衡的节点
        private Node<T> node;
    }

    public static void main(String[] args) {
        AVLTree<Integer> bsTree = new AVLTree<Integer>();
        /*bsTree.add(25);
        bsTree.add(16);
        bsTree.add(69);
        bsTree.add(5);
        bsTree.add(24);
        bsTree.add(32);
        bsTree.add(90);
        bsTree.add(19);
        bsTree.add(78);
        bsTree.add(20);
        bsTree.add(1);
        bsTree.add(2);
        bsTree.add(3);
        bsTree.add(4);
        bsTree.add(5);
        bsTree.add(6);
        bsTree.add(7);
        bsTree.add(8);*/
        bsTree.add(1);
        bsTree.add(2);
        bsTree.add(3);
        /*bsTree.add(25);
        bsTree.add(80);
        bsTree.add(16);
        bsTree.add(60);
        bsTree.add(75);
        bsTree.add(90);
        bsTree.add(30);
        bsTree.add(78);
        bsTree.add(85);
        bsTree.add(98);
        bsTree.add(82);
        bsTree.printTree();*/
        bsTree.delete(2);
        bsTree.printTree();
    }

}
