package three;

import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

public class LRUCacheDemo2<K, V> {
    class Node<K, V> {
        K key;
        V value;
        Node<K, V> prev;
        Node<K, V> next;

        public Node() {

        }

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        Node node = doubleLinkedList.head.next;
        while(node.key != null){
            sb.append("{"+node.key+":"+node.value+"}");
            node = node.next;
        }
        return sb.toString();
    }

    class DoubleLinkedList<K, V> {
        Node<K, V> head;
        Node<K, V> tail;

        public DoubleLinkedList() {
            head = new Node<K, V>();
            tail = new Node<K, V>();
            head.next = tail;
            tail.prev = head;
        }

        public void addHead(Node<K, V> node) {
            Node<K, V> oldNode = head.next;
            node.next = oldNode;
            oldNode.prev = node;
            node.prev = head;
            ;
            head.next = node;
        }

        public void removeNode(Node<K, V> node) {
            node.next.prev = node.prev;
            node.prev.next = node.next;
        }

        public Node<K, V> getLast() {
            return tail.prev;
        }
    }

    private int cacheSize;
    Map<Integer, Node<Integer, Integer>> map;
    DoubleLinkedList<Integer, Integer> doubleLinkedList;

    public LRUCacheDemo2(int cacheSize) {
        this.cacheSize = cacheSize;
        map = new HashMap<Integer, Node<Integer, Integer>>();
        doubleLinkedList = new DoubleLinkedList<Integer, Integer>();
    }

    public int get(int key) {
        if (map.containsKey(key)) {
            return -1;
        }
        Node<Integer, Integer> node = map.get(key);
        doubleLinkedList.removeNode(node);
        doubleLinkedList.addHead(node);
        return node.value;
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) {
            Node<Integer, Integer> node = map.get(key);
            node.value = value;

            doubleLinkedList.removeNode(node);
            doubleLinkedList.addHead(node);
        } else {
            if (map.size() >= cacheSize) {
                Node<Integer, Integer> lastNode = doubleLinkedList.getLast();
                map.remove(lastNode.key);
                doubleLinkedList.removeNode(lastNode);
            }
            Node<Integer, Integer> node = new Node<Integer, Integer>(key, value);
            map.put(key,node);
            doubleLinkedList.addHead(node);
        }
    }

    public static void main(String[] args) {
        LRUCacheDemo2<Integer,Integer> lruCacheDemo = new LRUCacheDemo2<Integer,Integer>(3);
        lruCacheDemo.put(1,2);
        lruCacheDemo.put(2,3);
        lruCacheDemo.put(3,4);
        System.out.println(lruCacheDemo);
        lruCacheDemo.put(2,4);
        System.out.println(lruCacheDemo);
        lruCacheDemo.put(5,6);
        System.out.println(lruCacheDemo);
    }
}
