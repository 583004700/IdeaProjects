package chapter3.datastructure;

import lombok.Getter;
import lombok.Setter;

/**
 * 链队列
 * @param <T>
 */
public class LinkQueue<T>{

    private Node<T> front;
    private Node<T> rear;

    @Setter
    @Getter
    public static class Node<T>{
        private T data;
        private Node<T> next;

        public static <T> Node<T> getInstance(T ele){
            Node<T> node = new Node<T>();
            node.data = ele;
            return node;
        }
    }

    public LinkQueue(){

    }

    public void enQueue(T ele){
        Node<T> instance = Node.getInstance(ele);
        if(front == rear && front == null){
            front = instance;
        }else{
            rear.next = instance;
        }
        rear = instance;
    }

    public T deQueue(){
        Node<T> node = front;
        if(node != null){
            front = front.next;
            if(front == null){
                this.rear = null;
            }
            return node.data;
        }
        return null;
    }

    public T getHead(){
        Node<T> node = front;
        if(node != null){
            return node.data;
        }
        return null;
    }
}
