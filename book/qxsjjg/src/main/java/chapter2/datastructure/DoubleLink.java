package chapter2.datastructure;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DoubleLink<T> {
    @Setter
    @Getter
    public static class Node<T> {
        private T data;
        private DoubleLink.Node<T> next;
        private DoubleLink.Node<T> pre;

        public static <T> DoubleLink.Node<T> getInstance(T element){
            DoubleLink.Node<T> node = new DoubleLink.Node<T>();
            node.data = element;
            return node;
        }
    }

    private Node<T> head;
    private Node<T> tail;

    public DoubleLink(){
        Node<T> node = Node.getInstance(null);
        this.head = node;
        this.tail = head;
    }

    public void addFirst(T element){
        Node<T> node = Node.getInstance(element);
        node.next = this.head.next;
        if(this.head.next != null) {
            this.head.next.pre = node;
        }else{
            this.tail = node;
        }
        this.head.next = node;
        node.pre = this.head;
    }

    public void addLast(T element){
        Node<T> node = Node.getInstance(element);
        this.tail.next = node;
        node.pre = this.tail;
        this.tail = node;
    }

    public void add(int index,T element){
        int count = 0;
        Node<T> node = this.head;
        while(count++<=index){
            node = node.next;
        }
        Node<T> instance = Node.getInstance(element);
        Node<T> temp = node.pre;
        node.pre = instance;
        instance.pre = temp;
        temp.next = instance;
        instance.next = node;
    }

    public Node<T> getNode(int index){
        int count = 0;
        Node<T> node = this.head.next;
        while(count++<index){
            node = node.next;
        }
        return node;
    }

    public T get(int index){
        Node<T> node = getNode(index);
        if(node != null){
            return node.data;
        }
        return null;
    }

    public T remove(int index){
        Node<T> current = getNode(index);
        Node<T> pre = current.pre;
        Node<T> next = current.next;
        if(pre != null){
            pre.next = next;
        }
        if(next != null){
            next.pre = pre;
        }else{
            this.tail = pre;
        }
        return current.getData();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        DoubleLink.Node<T> n = this.head.next;
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
