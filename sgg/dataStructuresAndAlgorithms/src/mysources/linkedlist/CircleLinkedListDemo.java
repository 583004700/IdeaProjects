package mysources.linkedlist;

public class CircleLinkedListDemo {
    public static void main(String[] args) {
        CircleLinkedList circleLinkedList = new CircleLinkedList();
        circleLinkedList.addNode(new Node(1));
        circleLinkedList.addNode(new Node(2));
        circleLinkedList.addNode(new Node(3));
        circleLinkedList.addNode(new Node(4));
        circleLinkedList.addNode(new Node(5));

        circleLinkedList.ysf();
    }
}

class CircleLinkedList{
    public Node head;
    public Node tail;

    public void addNode(Node node){
        if(head == null){
            head = node;
            tail = node;
        }
        tail.next = node;
        node.next = head;
        tail = node;
    }

    /**
     * 约瑟夫问题
     */
    public void ysf(){
        Node start = head;
        Node pre = tail;
        int index = 1;
        while(pre != start){
            if(index == 2){
                System.out.println(start.id);
                pre.next = start.next;
                index = 1;
                start = start.next;
                continue;
            }
            index++;
            pre = start;
            start = start.next;
        }
        System.out.println(start.id);
    }
}

class Node{
    public int id;
    public Node next;

    public Node(int id) {
        this.id = id;
    }
}
