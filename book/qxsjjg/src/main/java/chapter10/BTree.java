package chapter10;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * B树
 */
public class BTree<T extends Comparable<T>> {

    private Node<T> root;
    // 阶数
    private int order;

    public BTree(int order) {
        this.order = order;
    }

    public void add(T data) {
        if(search(data) != null){
            // 节点已经存在
            return;
        }
        if (root == null) {
            Node<T> node = Node.getInstance(data, order);
            this.root = node;
        } else {
            Node<T> tNode = searchInsertNode(data);
            // 如果上溢
            Node.Up<T> up = tNode.add(data);
            while (up != null) {
                List<Node<T>> listNode = up.getList();
                Node<T> pNode = searchParentNode(tNode);
                if (pNode == null) {
                    pNode = new Node<>(this.order);
                    this.root = pNode;
                }
                pNode.removeChildNode(tNode);
                pNode.addChildNode(listNode.get(0));
                pNode.addChildNode(listNode.get(1));
                tNode = pNode;
                up = pNode.add(up.getValue());
                printTree();
            }
            printTree();
        }
    }

    public T search(T data){
        return doSearch(root,data);
    }

    /**
     * 查找节点，找不到返回null
     * @param start
     * @param data
     * @return
     */
    public T doSearch(Node<T> start,T data) {
        if(start == null){
            return null;
        }
        T min = start.getMin();
        T max = start.getMax();
        if (data.compareTo(min) < 0) {
            Node<T> left = start.getLeftNode(min);
            if (left != null) {
                return doSearch(left, data);
            }
        } else if (data.compareTo(max) > 0) {
            Node<T> right = start.getRightNode(max);
            if (right != null) {
                return doSearch(right, data);
            }
        } else {
            int index = start.searchInsertIndex(data);
            if(start.getDataList().size() != 0){
                if(start.getDataList().get(index).compareTo(data) == 0){
                    return start.getDataList().get(index);
                }
            }
            if (start.getChildrenList().size() != 0) {
                Node<T> searchNode = start.getChildrenList().get(index);
                return doSearch(searchNode, data);
            } else {
                int i = start.searchIndex(data);
                if(i != -1){
                    return start.getDataList().get(i);
                }
            }
        }
        return null;
    }

    /**
     * 查找父节点，如果查找不到返回null
     * @param node
     * @return
     */
    public Node<T> searchParentNode(Node<T> node) {
        return doSearchParentNode(root, node);
    }

    public static void main(String[] args) {
        BTree<Integer> bTree = new BTree<Integer>(3);
        bTree.add(30);
        bTree.add(65);
        bTree.add(18);
        bTree.add(45);
        bTree.add(50);
        bTree.add(80);
        bTree.add(10);
        bTree.add(23);
        bTree.add(37);
        bTree.add(40);
        bTree.add(48);
        bTree.add(59);
        bTree.add(77);
        bTree.add(95);

        // {53, 139, 75, 49, 145, 36, 101}
        /*bTree.add(53);
        bTree.add(139);
        bTree.add(75);
        bTree.add(49);
        bTree.add(145);
        bTree.add(36);
        bTree.add(101);*/
        bTree.printTree();

        System.out.println(bTree.search(30));
        System.out.println(bTree.search(65));
        System.out.println(bTree.search(18));
        System.out.println(bTree.search(45));
        System.out.println(bTree.search(50));
        System.out.println(bTree.search(80));
        System.out.println(bTree.search(10));
        System.out.println(bTree.search(23));
        System.out.println(bTree.search(37));
        System.out.println(bTree.search(40));
        System.out.println(bTree.search(48));
        System.out.println(bTree.search(59));
        System.out.println(bTree.search(77));
        System.out.println(bTree.search(95));

        System.out.println(bTree.search(345));
        System.out.println(bTree.search(89));
        System.out.println(bTree.search(78));
    }

    public Node<T> searchInsertNode(T data) {
        return doSearchInsertNode(this.root, data);
    }

    private Node<T> doSearchParentNode(Node<T> start, Node<T> node) {
        if (start.getChildrenList().contains(node)) {
            return start;
        }
        for (Node<T> tNode : start.getChildrenList()) {
            Node<T> p = doSearchParentNode(tNode, node);
            if (p != null) {
                return p;
            }
        }
        return null;
    }

    private Node<T> doSearchInsertNode(Node<T> start, T data) {
        T min = start.getMin();
        T max = start.getMax();
        if (data.compareTo(min) < 0) {
            Node<T> left = start.getLeftNode(min);
            if (left != null) {
                return doSearchInsertNode(left, data);
            }
        } else if (data.compareTo(max) > 0) {
            Node<T> right = start.getRightNode(max);
            if (right != null) {
                return doSearchInsertNode(right, data);
            }
        } else {
            int index = start.searchInsertIndex(data);
            if (start.getChildrenList().size() != 0) {
                Node<T> searchNode = start.getChildrenList().get(index);
                return doSearchInsertNode(searchNode, data);
            } else {
                return start;
            }
        }
        return start;
    }

    public void printTree() {
        Map<Integer, List<BTree.Node<T>>> map = new HashMap<Integer, List<BTree.Node<T>>>();
        List<BTree.Node<T>> first = new ArrayList<BTree.Node<T>>();
        first.add(root);
        map.put(1, first);
        int level = 1;
        while (map.containsKey(level)) {
            List<BTree.Node<T>> nodes = map.get(level);
            Integer nextKey = level + 1;
            for (BTree.Node<T> node : nodes) {
                if (!map.containsKey(nextKey)) {
                    map.put(nextKey, new ArrayList<BTree.Node<T>>());
                }
                List<BTree.Node<T>> nextNodes = map.get(nextKey);
                nextNodes.addAll(node.getChildrenList());
            }
            level++;
        }
        level = 1;
        while (map.containsKey(level)) {
            List<BTree.Node<T>> nodes = map.get(level);
            for (BTree.Node<T> node : nodes) {
                System.out.print(node);
            }
            System.out.println();
            level++;
        }
    }

    @Setter
    @Getter
    @Accessors(chain = true)
    public static class Node<T extends Comparable<T>> implements Comparable<Node<T>> {
        // 关键字
        private List<T> dataList = new ArrayList<T>();
        // 子节点
        private List<Node<T>> childrenList = new ArrayList<Node<T>>();
        // 阶数
        private int order;

        public Node(int order) {
            this.order = order;
        }

        public static <T extends Comparable<T>> Node<T> getInstance(T data, int order) {
            Node<T> node = new Node<T>(order);
            node.getDataList().add(data);
            return node;
        }

        @Override
        public String toString() {
            return dataList.toString() + "  |  ";
        }

        /**
         * 获取最小的关键字
         *
         * @return
         */
        public T getMin() {
            if (dataList.size() > 0) {
                return dataList.get(0);
            }
            return null;
        }

        /**
         * 获取最大的关键字
         *
         * @return
         */
        public T getMax() {
            if (dataList.size() > 0) {
                return dataList.get(dataList.size() - 1);
            }
            return null;
        }

        public Node<T> getLeftNode() {
            T min = getMin();
            if (min != null) {
                return getLeftNode(min);
            }
            return null;
        }

        public Node<T> getRightNode() {
            T max = getMax();
            if (max != null) {
                return getLeftNode(max);
            }
            return null;
        }

        /**
         * 得到某个关键字左子树节点
         *
         * @param data
         * @return
         */
        public Node<T> getLeftNode(T data) {
            int i = searchIndex(data);
            if (i >= 0 && i < childrenList.size()) {
                return childrenList.get(i);
            }
            return null;
        }

        /**
         * 得到某个关键字右子树节点
         *
         * @param data
         * @return
         */
        public Node<T> getRightNode(T data) {
            int i = searchIndex(data) + 1;
            if (i >= 0 && i < childrenList.size()) {
                return childrenList.get(i);
            }
            return null;
        }

        /**
         * 查找某个关键字所在的下标
         *
         * @param data
         * @return
         */
        public int searchIndex(T data) {
            return doSearchIndex(data, 0, dataList.size() - 1);
        }

        @Setter
        @Getter
        public static class Up<T extends Comparable<T>> {
            private T value;
            List<Node<T>> list = new ArrayList<>();
        }

        /**
         * 如果不发生上溢，返回null，如果上溢，返回被拆分的两个Node对象
         *
         * @param data
         * @return
         */
        public Up<T> add(T data) {
            int i = searchInsertIndex(data);
            this.dataList.add(i, data);
            if (dataList.size() >= order) {
                Up<T> up = new Up<>();
                int middle = this.dataList.size() / 2;
                T middleData = this.getDataList().get(middle);
                up.setValue(middleData);
                int i1 = searchNodeInsertIndex(middleData);
                Node<T> l1 = new Node<>(this.order);
                Node<T> l2 = new Node<>(this.order);
                if (i1 != -1) {
                    for (int j = 0; j < i1; j++) {
                        l1.addChildNode(childrenList.get(j));
                    }
                    for (int j = i1; j < childrenList.size(); j++) {
                        l2.addChildNode(this.childrenList.get(j));
                    }
                }

                int k1 = searchIndex(middleData);
                for (int j = 0; j < k1; j++) {
                    l1.add(dataList.get(j));
                }
                for (int j = k1 + 1; j < dataList.size(); j++) {
                    l2.add(dataList.get(j));
                }
                up.getList().add(l1);
                up.getList().add(l2);
                return up;
            }
            return null;
        }

        /**
         * 移除子节点
         *
         * @param node
         */
        public void removeChildNode(Node<T> node) {
            this.childrenList.remove(node);
        }

        public void addChildNode(Node<T> node) {
            int i = searchNodeInsertIndex(node.getDataList().get(0));
            this.childrenList.add(i, node);
        }

        public int searchNodeInsertIndex(T data) {
            Node<T> node = new Node<>(this.order);
            node.getDataList().add(data);
            node.getChildrenList().add(node);
            return doSearchNodeInsertIndex(node, 0, childrenList.size() - 1);
        }

        private int doSearchNodeInsertIndex(Node<T> data, int startIndex, int endIndex) {
            if (startIndex > endIndex) {
                return startIndex;
            }
            int middle = (startIndex + endIndex) / 2;
            Node<T> middleData = childrenList.get(middle);
            if (data.compareTo(middleData) < 0) {
                return doSearchNodeInsertIndex(data, startIndex, middle - 1);
            } else if (data.compareTo(middleData) > 0) {
                return doSearchNodeInsertIndex(data, middle + 1, endIndex);
            } else {
                return middle;
            }
        }

        public int searchInsertIndex(T data) {
            return doSearchInsertIndex(data, 0, dataList.size() - 1);
        }

        /**
         * 使用二分法查找
         *
         * @param data
         * @param startIndex
         * @param endIndex
         * @return
         */
        private int doSearchIndex(T data, int startIndex, int endIndex) {
            if (startIndex > endIndex) {
                return -1;
            }
            int middle = (startIndex + endIndex) / 2;
            T middleData = dataList.get(middle);
            if (data.compareTo(middleData) < 0) {
                return doSearchIndex(data, startIndex, middle - 1);
            } else if (data.compareTo(middleData) > 0) {
                return doSearchIndex(data, middle + 1, endIndex);
            } else {
                return middle;
            }
        }

        private int doSearchInsertIndex(T data, int startIndex, int endIndex) {
            if (startIndex > endIndex) {
                return startIndex;
            }
            int middle = (startIndex + endIndex) / 2;
            T middleData = dataList.get(middle);
            if (data.compareTo(middleData) < 0) {
                return doSearchInsertIndex(data, startIndex, middle - 1);
            } else if (data.compareTo(middleData) > 0) {
                return doSearchInsertIndex(data, middle + 1, endIndex);
            } else {
                return middle;
            }
        }

        @Override
        public int compareTo(Node<T> o) {
            return this.getDataList().get(0).compareTo(o.getDataList().get(0));
        }
    }

}
