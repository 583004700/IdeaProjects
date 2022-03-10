package chapter2;

import lombok.Getter;
import lombok.Setter;

/**
 * 链表
 */
@Setter
@Getter
public class Link<T> {

    private Node<T> head;
    private Node<T> tail;

    @Setter
    @Getter
    public static class Node<T> {
        private T data;
        private Node<T> next;

        public static <T> Node<T> getInstance(T element){
            Node<T> node = new Node<T>();
            node.data = element;
            return node;
        }
    }

    public Link() {
        this.head = new Node<T>();
        this.tail = head;
    }

    public void addFirst(T element){
        Node<T> node = Node.getInstance(element);
        node.next = head.next;
        head.next = node;
    }

    public void addLast(T element){
        Node<T> node = Node.getInstance(element);
        tail.next = node;
        tail = node;
    }

    public void add(int index,T element){
        int count = 0;
        Node<T> node = Node.getInstance(element);
        Node<T> pre = this.head;
        while(count++<index){
            pre = pre.next;
        }
        node.next = pre.next;
        pre.next = node;
    }

    public T get(int index){
        Node<T> node = getNode(index);
        if(node != null){
            return node.data;
        }
        return null;
    }

    public Node<T> getNode(int index){
        int count = 0;
        Node<T> result = this.head.next;
        while(count++<index){
            result = result.next;
        }
        return result;
    }

    public T remove(int index){
        Node<T> node = getNode(index - 1);
        Node<T> result = node.next;
        node.next = node.next.next;
        if(node.next == null){
            this.tail = node;
        }
        return result.data;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Node<T> n = this.head.next;
        while(n != null){
            sb.append(n.data);
            sb.append(",");
            n = n.next;
        }
        sb.append("]");
        sb.append("head:"+this.head.data+",");
        sb.append("tail:"+this.tail.data);
        return sb.toString();
    }
}
