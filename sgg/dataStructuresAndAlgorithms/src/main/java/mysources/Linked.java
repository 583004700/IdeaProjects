package mysources;

public class Linked {
    public static Node node = new Node(1);
    public static Node head = new Node(0);
    public static int k = 3;
    static{
        head.next = node;
        node.next = new Node(2);
        node.next.next = new Node(3);
        node.next.next.next = new Node(4);
        node.next.next.next.next = new Node(5);
        node.next.next.next.next.next = new Node(6);
        node.next.next.next.next.next.next = new Node(7);
    }

    public static void print(){
        Node current = head;
        while(current != null){
            System.out.print(current.value+"\t");
            current = current.next;
        }
    }

    /**
     * 反转
     */
    public static void re(){

        Node pre = node;
        Node current = pre.next;
        Node next = current.next;
        Node first = node;

        for (int i = 0; i < k; i++) {
            if (i == k - 2) {
                head = current;
            }
            if (i != k - 1) {
                current.next = pre;
            } else {
                first.next = current;
                continue;
            }
            pre = current;
            current = next;
            next = current.next;
        }
    }

    public static void main(String[] args) {
        print();
        re();
        System.out.println();
        print();
    }
}

class Node{
    public int value;
    public Node next;

    public Node(int value) {
        this.value = value;
    }
}
