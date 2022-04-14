package chapter6.datastructure.huffmantree;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        HuffmanTree.Node<String> a = new HuffmanTree.Node<String>();
        a.setData("a").setWeight(5);
        HuffmanTree.Node<String> b = new HuffmanTree.Node<String>();
        b.setData("b").setWeight(32);
        HuffmanTree.Node<String> c = new HuffmanTree.Node<String>();
        c.setData("c").setWeight(18);
        HuffmanTree.Node<String> d = new HuffmanTree.Node<String>();
        d.setData("d").setWeight(7);
        HuffmanTree.Node<String> e = new HuffmanTree.Node<String>();
        e.setData("e").setWeight(25);
        HuffmanTree.Node<String> f = new HuffmanTree.Node<String>();
        f.setData("f").setWeight(13);
        List<HuffmanTree.Node<String>> nodes = new ArrayList<HuffmanTree.Node<String>>();
        nodes.add(a);
        nodes.add(b);
        nodes.add(c);
        nodes.add(d);
        nodes.add(e);
        nodes.add(f);
        HuffmanTree<String> huffmanTree = new HuffmanTree<String>(nodes);
        huffmanTree.getCodes();
    }
}
