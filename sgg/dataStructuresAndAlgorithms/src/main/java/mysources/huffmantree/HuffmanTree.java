package mysources.huffmantree;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

public class HuffmanTree {
    public static void main(String[] args) {
        LinkedList<Integer> arr = new LinkedList<Integer>(Arrays.asList(13,7,8,3,29,6,1));

        HuffmanTree huffmanTree = new HuffmanTree();
        Node node = huffmanTree.buildHuffmanTree(arr);
        System.out.println(node);
    }

    public Node buildHuffmanTree(LinkedList<Integer> arr){
        LinkedList<Node> nodes = new LinkedList<Node>();
        for(int i : arr){
            nodes.add(new Node(i));
        }
        Collections.sort(nodes);


        Node first = null;
        Node second = null;
        while((first = nodes.poll()) != null && (second = nodes.poll()) != null){
            Node parent = new Node(first.getValue()+second.getValue());
            parent.setLeft(first);
            parent.setRight(second);
            nodes.addFirst(parent);
            Collections.sort(nodes);
        }
        return first;
    }
}

class Node implements Comparable<Node>{
    private int value;
    private Node left;
    private Node right;

    public Node(int value){
        this.value = value;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }

    @Override
    public int compareTo(Node o) {
        return this.value - o.value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }
}