package chapter10;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * B+树
 */
public class BPTree<T extends Comparable<T>> {
    // 根节点
    private Node<T> root;
    // 阶数
    private int order;

    public BPTree(int order) {
        this.order = order;
    }

    public void add(T data) {
        if (search(data) != null) {
            // 节点已经存在
            return;
        }
        if (root == null) {
            Node<T> node = Node.getInstance(data, order);
            this.root = node;
        } else {
            Node<T> tNode = searchInsertNode(data);
            boolean isMax = data.compareTo(tNode.getMax()) > 0;
            // 如果上溢
            Node.Up<T> up = tNode.add(data);
            if (up != null) {
                Node<T> n1 = up.getList().get(0);
                Node<T> n2 = up.getList().get(1);
                n1.setNext(n2);
                Node<T> leftBrother = tNode.getPre();
                if (leftBrother != null) {
                    leftBrother.setNext(n1);
                }
                Node<T> rightBrother = tNode.getNext();
                n2.setNext(rightBrother);
            }
            if (isMax) {
                // 如果插入的点是最大值，则要更新所有父节点的最大值
                Node<T> temp = tNode;
                while (temp != null) {
                    Node<T> p = searchParentNode(temp);
                    if (p != null) {
                        p.setMax(data);
                    }
                    temp = p;
                }
            }

            while (up != null) {
                List<Node<T>> listNode = up.getList();
                Node<T> pNode = searchParentNode(tNode);
                if (pNode == null) {
                    pNode = new Node<>(this.order);
                    this.root = pNode;
                    pNode.add(listNode.get(1).getMax());
                }
                pNode.removeChildNode(tNode);
                pNode.addChildNode(listNode.get(0));
                pNode.addChildNode(listNode.get(1));
                tNode = pNode;
                up = pNode.add(up.getValue());
            }
        }
    }

    public void delete(T data) {
        Node<T> tNode = searchNode(data);
        if (tNode == null) {
            return;
        }
        if (root.getDataList().size() == 1 && root.getChildrenList().size() == 0) {
            root = null;
            return;
        }
        if (root.getChildrenList().size() == 0) {
            root.removeData(data);
            return;
        }
        if (tNode.getLeftNode() != null) {
            Node<T> leftMax = getMax(tNode.getLeftNode(data));
            T leftMaxData = leftMax.getMax();
            tNode.removeData(data);
            tNode.add(leftMaxData);
            if (leftMax.getDataList().size() == 1) {
                down(leftMax);
            }
            leftMax.removeData(leftMaxData);
        } else {
            if (tNode.getDataList().size() == 1) {
                down(tNode);
            }
            tNode.removeData(data);
        }
    }

    // 下溢
    public void down(Node<T> node) {
        Node<T> leftBrother = getLeftBrother(node);
        Node<T> rightBrother = getRightBrother(node);
        Node<T> parent = searchParentNode(node);

        Node<T> borrow = leftBrother != null ? leftBrother : rightBrother;

        if (borrow != rightBrother && rightBrother != null && rightBrother.getDataList().size() > 1) {
            borrow = rightBrother;
        }
        T borrowData = null;
        int parentIndex = -1;
        Node<T> borrowNode = null;
        if (borrow == leftBrother) {
            borrowData = leftBrother.getMax();
            parentIndex = parent.searchNodeIndex(node) - 1;
            borrowNode = borrow.getRightNode();
        } else {
            borrowData = rightBrother.getMin();
            parentIndex = parent.searchNodeIndex(node);
            borrowNode = borrow.getLeftNode();
        }
        T parentData = parent.getDataList().get(parentIndex);
        if (borrow.getDataList().size() > 1) {
            node.add(parentData);
            parent.removeData(parentData);
            parent.add(borrowData);
            borrow.removeData(borrowData);
            if (borrowNode != null) {
                borrow.removeChildNode(borrowNode);
                node.addChildNode(borrowNode);
            }
        } else {
            borrow.add(parentData);
            if (node.getChildrenList().size() > 0) {
                for (Node<T> tNode : node.getChildrenList()) {
                    borrow.addChildNode(tNode);
                }
            }
            parent.removeChildNode(node);
            if (parent.getDataList().size() == 1 && parent != root) {
                down(parent);
            }
            if (parent == root && root.getChildrenList().size() == 1) {
                root = borrow;
            }
            parent.removeData(parentData);
        }
    }

    public static void main(String[] args) {
        BPTree<Integer> bTree = new BPTree<Integer>(4);
        Set<Integer> set = new HashSet<>();
        List<Integer> list = new ArrayList<>();
        Random r = new Random();
        int count = 1000000; // 添加多少个随机数
        int maxV = 2000000; // 随机数的范围
        for (int i = 0; i < count; ) {
            int k = r.nextInt(maxV);
            if (set.add(k)) {
                list.add(k);
                i++;
            }
        }
        long insertStartTime = System.currentTimeMillis();
        for (Integer i : list) {
            bTree.add(i);
        }
        System.out.println("插入"+count+"条数据耗时："+(System.currentTimeMillis()-insertStartTime));
        //System.out.println("list:"+list);
        //System.out.println("-------------------");
        //bTree.printTree();
        /*count = 0;
        // 测试删除后留多少个
        int l = 10;
        for (Integer i : set) {
            if (set.size()-count<=l) {
                break;
            }
            bTree.delete(i);
            System.out.println("删除了："+i);
            bTree.printTree();
            count++;
        }*/

        int min = 23;
        int max = 78;
        long selectStartTimeOld = System.currentTimeMillis();
        List<Integer> fw1 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Integer e = list.get(i);
            if(e.compareTo(min) >= 0 && e.compareTo(max) <= 0){
                fw1.add(e);
            }
        }
        System.out.println("查询数据耗时："+(System.currentTimeMillis()-selectStartTimeOld));
        Collections.sort(fw1);
        System.out.println("查询结果："+fw1);
        // 查询一定范围的数据
        long selectStartTime = System.currentTimeMillis();
        List<Integer> fw2 = bTree.searchScope(min,max);
        System.out.println("使用b+树查询数据耗时："+(System.currentTimeMillis()-selectStartTime));
        System.out.println("使用b+树查询结果"+fw2);
    }

    /**
     * 获取右兄弟节点
     *
     * @param node
     * @return
     */
    public Node<T> getRightBrother(Node<T> node) {
        Node<T> parent = searchParentNode(node);
        if (parent != null) {
            int index = parent.searchNodeIndex(node);
            if (index < parent.getChildrenList().size() - 1) {
                return parent.getChildrenList().get(index + 1);
            }
        }
        return null;
    }

    /**
     * 获取左兄弟节点
     *
     * @param node
     * @return
     */
    public Node<T> getLeftBrother(Node<T> node) {
        Node<T> parent = searchParentNode(node);
        if (parent != null) {
            int index = parent.searchNodeIndex(node);
            if (index > 0) {
                return parent.getChildrenList().get(index - 1);
            }
        }
        return null;
    }

    public T search(T data) {
        return doSearch(root, data);
    }

    public List<T> searchScope(T min, T max) {
        Node<T> startNode = searchInsertNode(min);
        List<T> list = startNode.getScope(min, max);
        return list;
    }

    public Node<T> getMin(Node<T> start) {
        Node<T> result = start;
        while (result.getLeftNode() != null) {
            result = result.getLeftNode();
        }
        return result;
    }

    public Node<T> getMax(Node<T> start) {
        // 没有子节点元素会比当前节点元素大
        return start;
    }

    /**
     * 得到关键字的左节点
     *
     * @param data
     * @return
     */
    public Node<T> getLeftNode(T data) {
        Node<T> node = searchNode(data);
        if (node != null) {
            return node.getLeftNode(data);
        }
        return null;
    }

    /**
     * 得到关键字的右节点
     *
     * @param data
     * @return
     */
    public Node<T> getRightNode(T data) {
        Node<T> node = searchNode(data);
        if (node != null) {
            return node.getRightNode(data);
        }
        return null;
    }

    /**
     * 查找关键字所在的node，如果不存在返回null
     *
     * @param data
     * @return
     */
    public Node<T> searchNode(T data) {
        return doSearchNode(root, data);
    }

    /**
     * 查找父节点，如果查找不到返回null
     *
     * @param node
     * @return
     */
    public Node<T> searchParentNode(Node<T> node) {
        return doSearchParentNode(root, node);
    }

    public Node<T> searchInsertNode(T data) {
        return doSearchInsertNode(this.root, data);
    }

    /**
     * 查找节点，找不到返回null
     *
     * @param start
     * @param data
     * @return
     */
    public T doSearch(Node<T> start, T data) {
        if (start == null) {
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
            return null;
        } else {
            int i = start.searchIndex(data);
            if (i != -1) {
                return start.getDataList().get(i);
            }
            int index = start.searchInsertIndex(data);
            if (start.getChildrenList().size() != 0) {
                Node<T> searchNode = start.getChildrenList().get(index);
                return doSearch(searchNode, data);
            }
        }
        return null;
    }

    private Node<T> doSearchNode(Node<T> start, T data) {
        if (start == null) {
            return null;
        }
        T min = start.getMin();
        T max = start.getMax();
        if (data.compareTo(min) < 0) {
            Node<T> left = start.getLeftNode();
            if (left != null) {
                return doSearchNode(left, data);
            }
        } else if (data.compareTo(max) > 0) {
            return null;
        } else {
            int i = start.searchIndex(data);
            if (i != -1) {
                return start;
            }
            int index = start.searchInsertIndex(data);
            if (start.getChildrenList().size() != 0) {
                Node<T> searchNode = start.getChildrenList().get(index);
                return doSearchNode(searchNode, data);
            }
        }
        return null;
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

    /**
     * 查找应该添加的节点
     *
     * @param start
     * @param data
     * @return
     */
    private Node<T> doSearchInsertNode(Node<T> start, T data) {
        T min = start.getMin();
        T max = start.getMax();
        if (data.compareTo(min) < 0) {
            Node<T> left = start.getLeftNode();
            if (left != null) {
                return doSearchInsertNode(left, data);
            }
        } else if (data.compareTo(max) > 0) {
            Node<T> right = start.getRightNode();
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
        Map<Integer, List<BPTree.Node<T>>> map = new HashMap<Integer, List<BPTree.Node<T>>>();
        List<BPTree.Node<T>> first = new ArrayList<BPTree.Node<T>>();
        first.add(root);
        map.put(1, first);
        int level = 1;
        while (map.containsKey(level)) {
            List<BPTree.Node<T>> nodes = map.get(level);
            Integer nextKey = level + 1;
            for (BPTree.Node<T> node : nodes) {
                if (!map.containsKey(nextKey)) {
                    map.put(nextKey, new ArrayList<BPTree.Node<T>>());
                }
                List<BPTree.Node<T>> nextNodes = map.get(nextKey);
                nextNodes.addAll(node.getChildrenList());
            }
            level++;
        }
        level = 1;
        while (map.containsKey(level)) {
            List<BPTree.Node<T>> nodes = map.get(level);
            for (BPTree.Node<T> node : nodes) {
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
        // 下一个节点
        private Node<T> next;
        // 上一个节点
        private Node<T> pre;

        public Node(int order) {
            this.order = order;
        }

        public void setNext(Node<T> next) {
            this.next = next;
            if (next != null) {
                next.pre = this;
            }
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

        public List<T> getScope(T min, T max) {
            List<T> result = new ArrayList<>();
            if (min.compareTo(this.getMin()) < 0) {
                return result;
            }
            Node<T> start = this;
            w:
            while (start != null && start.dataList.size() > 0) {
                for (T t : start.dataList) {
                    if (t.compareTo(min) >= 0 && t.compareTo(max) <= 0) {
                        result.add(t);
                    }
                    if(t.compareTo(max) > 0){
                        break w;
                    }
                }
                start = start.next;
            }
            return result;
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

        /**
         * 设置最大的关键字
         */
        public void setMax(T maxData) {
            if (dataList.size() > 0) {
                dataList.set(dataList.size() - 1, maxData);
            }
        }

        public Node<T> getLeftNode() {
            T min = getMin();
            if (min != null) {
                return getLeftNode(min);
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

        /**
         * 移除子节点
         *
         * @param node
         */
        public void removeChildNode(Node<T> node) {
            this.childrenList.remove(node);
        }

        public T removeData(T data) {
            int index = searchIndex(data);
            if (index != -1) {
                return this.getDataList().remove(index);
            }
            return null;
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
            Up<T> up = null;
            if (dataList.size() > order) {
                up = new Up<>();
                int middle = (this.dataList.size() - 1) / 2;
                T middleData = this.getDataList().get(middle);
                up.setValue(middleData);
                int i1 = searchNodeInsertIndex(middleData);
                Node<T> l1 = new Node<>(this.order);
                Node<T> l2 = new Node<>(this.order);
                if (i1 != -1) {
                    for (int j = 0; j < i1 && j < childrenList.size(); j++) {
                        l1.addChildNode(childrenList.get(j));
                    }
                    for (int j = i1; j < childrenList.size(); j++) {
                        l2.addChildNode(this.childrenList.get(j));
                    }
                }

                int k1 = searchIndex(middleData);
                for (int j = 0; j <= k1 && j < dataList.size(); j++) {
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

        // 此处和B树不同，最大数据的左边才是此节点的右子树节点
        public Node<T> getRightNode() {
            T max = getMax();
            if (max != null) {
                return getLeftNode(max);
            }
            return null;
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
         * 查找节点的下标
         *
         * @param node
         * @return
         */
        public int searchNodeIndex(Node<T> node) {
            return doSearchNodeIndex(node, 0, childrenList.size() - 1);
        }

        private int doSearchNodeIndex(Node<T> node, int startIndex, int endIndex) {
            if (startIndex > endIndex) {
                return -1;
            }
            int middle = (startIndex + endIndex) / 2;
            Node<T> middleData = childrenList.get(middle);
            if (node.compareTo(middleData) < 0) {
                return doSearchNodeIndex(node, startIndex, middle - 1);
            } else if (node.compareTo(middleData) > 0) {
                return doSearchNodeIndex(node, middle + 1, endIndex);
            } else {
                return middle;
            }
        }
    }

}
