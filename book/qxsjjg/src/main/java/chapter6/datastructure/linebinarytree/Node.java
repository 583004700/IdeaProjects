package chapter6.datastructure.linebinarytree;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Node<T> {
    private T data;
    private Node<T> left;
    private Node<T> right;

    // 0代表左节点，1代表前一个节点
    private int leftType;
    // 0代表右节点，1代表后一个节点
    private int rightType;

    public void setFront(Node<T> node){
        leftType = 1;
        left = node;
    }

    public void setNext(Node<T> node){
        rightType = 1;
        right = node;
    }
}
