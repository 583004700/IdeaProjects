package chapter10;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Arrays;
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

    private int minDataLength;

    public BPTree(int order) {
        this.order = order;
        minDataLength = (int) Math.ceil((double) this.order / 2);
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
            // 如果上溢
            Node.Up<T> up = addData(tNode, data);
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
        removeData(tNode, data);
        if (tNode.getDataList().size() < this.minDataLength) {
            down(tNode);
        }
    }

    /**
     * 从节点上删除关键字,node为最后一层数据节点
     *
     * @param node
     */
    private void removeData(Node<T> node, T data) {
        boolean isMax = data.compareTo(node.getMax()) == 0;
        node.removeData(data);
        T maxV = node.getMax();
        boolean isOne = node.getDataList().size() == 0;
        if (isOne) {
            maxV = data;
        }
        // 如果移除的点是最大值，则要更新所有父节点的最大值
        Node<T> temp = node;
        while (temp != null && isMax) {
            Node<T> p = searchParentNode(temp);
            if (p != null) {
                int i = p.searchNodeIndex(temp, false);
                //判断更新的父结点是不是最大值,必须放在设置值之前判断
                isMax = data.compareTo(p.getMax()) == 0;
                if (!isOne) {
                    p.getDataList().set(i, maxV);
                } else {
                    p.getDataList().remove(i);
                    p.getChildrenList().remove(i);
                }
                isOne = p.getDataList().size() == 0;
            }
            temp = p;
        }
    }

    /**
     * 从节点上添加关键字，node为最后一层数据节点
     *
     * @param node
     * @param data
     */
    private Node.Up<T> addData(Node<T> node, T data) {
        boolean isMax = data.compareTo(node.getMax()) > 0;
        // 如果插入的点是最大值，则要更新所有父节点的最大值
        Node<T> temp = node;
        while (temp != null && isMax) {
            Node<T> p = searchParentNode(temp);
            if (p != null) {
                int i = p.searchNodeIndex(temp, false);
                isMax = data.compareTo(p.getMax()) > 0;
                p.getDataList().set(i, data);
            }
            temp = p;
        }
        return node.add(data);
    }

    // 下溢
    public void down(Node<T> node) {
        Node<T> leftBrother = node.getPre();
        Node<T> rightBrother = node.getNext();
        Node<T> parent = searchParentNode(node);
        Node<T> borrow = leftBrother != null ? leftBrother : rightBrother;
        if (borrow == null) {
            leftBrother = getLeftBrother(node);
            rightBrother = getRightBrother(node);
            borrow = leftBrother != null ? leftBrother : rightBrother;
        }
        T borrowData = null;
        Node<T> borrowNode = null;
        if (borrow != rightBrother && rightBrother != null && rightBrother.getDataList().size() > minDataLength) {
            borrow = rightBrother;
        }
        if (borrow == leftBrother) {
            borrowData = leftBrother.getMax();
            borrowNode = leftBrother.getRightNode();
        } else {
            borrowData = rightBrother.getMin();
            borrowNode = rightBrother.getLeftNode();
        }
        if (borrow.getDataList().size() > minDataLength) {
            addData(node, borrowData);
            removeData(borrow, borrowData);
            if (borrowNode != null) {
                node.addChildNode(borrowNode);
                borrow.removeChildNode(borrowNode);
            }
        } else {
            //int count = node.getDataList().size() - 1;
            int count = 0;
            for (int i = 0; i < node.getDataList().size(); ) {
                T t = node.getDataList().get(i);
                addData(borrow, t);
                if (node.getChildrenList().size() > 0) {
                    borrow.addChildNode(node.getChildrenList().get(count++));
                }
                removeData(node, t);
            }
            if (parent.getDataList().size() == 1 && parent == root) {
                root = parent.getLeftNode();
            } else if (parent.getDataList().size() < minDataLength) {
                if (parent != root) {
                    down(parent);
                }
            }
            // 需要维护next
            if (node.getPre() != null) {
                node.getPre().setNext(node.getNext());
            } else if (node.getNext() != null) {
                node.getNext().setPre(null);
            }
        }
    }

    public static void main(String[] args) {
        BPTree<Integer> bTree = new BPTree<Integer>(100);
        Set<Integer> set = new HashSet<>();
        //Set<Integer> set = new HashSet<>(Arrays.asList(0, 66, 3, 68, 4, 6, 71, 75, 13, 77, 78, 17, 83, 21, 22, 86, 24, 88, 27, 34));
        List<Integer> list = new ArrayList<>();
        //List<Integer> list = Arrays.asList(13, 34, 54, 38, 17, 3, 68, 59, 83, 21, 71, 22, 48, 86, 77, 78, 52, 42, 62, 0, 63, 46, 66, 4, 6, 27, 75, 24, 58, 88);
        Random r = new Random();
        int count = 200000; // 添加多少个随机数
        int maxV = 900000; // 随机数的范围
        for (int i = 0; i < count; ) {
            int k = r.nextInt(maxV);
            if (set.add(k)) {
                list.add(k);
                i++;
            }
        }
        long insertStartTime = System.currentTimeMillis();
        for (int i = 0; i < list.size(); i++) {
            bTree.add(list.get(i));
        }
        //bTree.printTree();
        System.out.println("插入" + count + "条数据耗时：" + (System.currentTimeMillis() - insertStartTime));
        //System.out.println("list:" + list);
        //System.out.println("-------------------");
        //bTree.printTree();
        count = 0;
        // 测试删除后留多少个
        int l = 100000;
        long deleteTime = System.currentTimeMillis();
        for (Integer i : set) {
            if (set.size() - count <= l) {
                break;
            }
            //System.out.println("删除：" + i);
            bTree.delete(i);
            list.remove(i);
            //System.out.println("删除了：" + i);
            //bTree.printTree();
            count++;
        }
        System.out.println("删除数据"+(set.size()-l)+"条数据耗时："+(System.currentTimeMillis()-deleteTime));
        //Collections.sort(list);
        //System.out.println(list);
        //System.out.println("-----------------------------");
        //bTree.printTree();

        int min = 1;
        int max = 450000;
        long selectStartTimeOld = System.currentTimeMillis();
        List<Integer> fw1 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Integer e = list.get(i);
            if (e.compareTo(min) >= 0 && e.compareTo(max) <= 0) {
                fw1.add(e);
            }
        }
        System.out.println("查询数据耗时：" + (System.currentTimeMillis() - selectStartTimeOld));
        Collections.sort(fw1);
        System.out.println("查询结果条数：" + fw1.size());
        // 查询一定范围的数据
        long selectStartTime = System.currentTimeMillis();
        List<Integer> fw2 = bTree.searchScope(min, max);
        System.out.println("使用b+树查询数据耗时：" + (System.currentTimeMillis() - selectStartTime));
        System.out.println("使用b+树查询结果条数:" + fw2.size());
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
            int index = parent.searchNodeIndex(node, true);
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
            int index = parent.searchNodeIndex(node, true);
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
            if (i != -1 && start.getChildrenList().size() == 0) {
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
            // 这里与B树不同，B+树所有数据在叶子节点
            if (i != -1 && start.getChildrenList().size() == 0) {
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
        if(root == null){
            System.out.println("null");
            return;
        }
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
            Node<T> start = this;
            w:
            while (start != null && start.dataList.size() > 0) {
                for (T t : start.dataList) {
                    if (t.compareTo(min) >= 0 && t.compareTo(max) <= 0) {
                        result.add(t);
                    }
                    if (t.compareTo(max) > 0) {
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

        public void removeDataAndChildrenNode(T data) {
            int i = searchIndex(data);
            getDataList().remove(i);
            getChildrenList().remove(i);
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
        public int searchNodeIndex(Node<T> node, boolean merge) {
            if (merge) {
                return doSearchNodeIndex(node, 0, childrenList.size() - 1);
            } else {
                return doSearchNodeIndexByLine(node);
            }
        }

        /**
         * 不使用二分查找，因为对于增加和删除节点数据后，影响节点在父节点中的位置
         *
         * @return
         */
        private int doSearchNodeIndexByLine(Node<T> node) {
            for (int i = 0; i < getChildrenList().size(); i++) {
                if (getChildrenList().get(i) == node) {
                    return i;
                }
            }
            return -1;
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
