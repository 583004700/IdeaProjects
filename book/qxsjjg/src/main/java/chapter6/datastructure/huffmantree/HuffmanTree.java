package chapter6.datastructure.huffmantree;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 哈夫曼树
 */
@Setter
@Getter
public class HuffmanTree<T> {

    private Node<T> root;

    @Setter
    @Getter
    @Accessors(chain = true)
    public static class Node<T>{
        private T data;
        // 出现的频率（权重）
        private int weight;
        private Node<T> left;
        private Node<T> right;
    }

    /**
     * 构建哈夫曼树
     * @param nodes
     */
    public HuffmanTree(List<Node<T>> nodes){
        while(nodes.size() > 1) {
            Collections.sort(nodes, Comparator.comparingInt(Node::getWeight));
            Node<T> a = nodes.remove(0);
            Node<T> b = nodes.remove(0);
            Node<T> ab = new Node<T>();
            ab.setWeight(a.getWeight()+b.getWeight());
            ab.setLeft(a);
            ab.setRight(b);
            nodes.add(ab);
        }
        this.root = nodes.get(0);
    }

    /**
     * 得到每个节点对应的编码
     */
    public void getCodes(){
        doGetCodes(this.root,"");
    }

    private void doGetCodes(Node<T> node,String lr){
        if(node != null && node.getData() != null){
            System.out.println(node.getData()+":"+lr);
        }
        if(node.getLeft() != null){
            doGetCodes(node.getLeft(),lr+"0");
        }
        if(node.getRight() != null){
            doGetCodes(node.getRight(),lr+"1");
        }
    }
}
