package chapter3.datastructure;

import lombok.Getter;
import lombok.Setter;

/**
 * 链栈
 * @param <T>
 */
public class LinkStack<T> {

    private Node<T> head;
    private Node<T> top;

    @Setter
    @Getter
    public static class Node<T>{
        private T data;
        private Node<T> p;
    }

    public LinkStack(){
        head = new Node<T>();
        head.p = head;
        top = head;
    }

    public void push(T ele){
        Node<T> node = new Node<T>();
        node.data = ele;
        node.p = top;
        top = node;
    }

    public T pop(){
        if(head == top){
            return null;
        }
        T data = top.data;
        top = top.p;
        return data;
    }

    public T getTop(){
        if(top!=null){
            return top.data;
        }
        return null;
    }
}
