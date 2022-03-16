package chapter3.datastructure;

public class Test {
    public static void main(String[] args) {
        LinkStack<Integer> ls = new LinkStack<Integer>();
        ls.push(3);
        ls.push(5);
        ls.push(7);
        ls.push(9);
        System.out.println(ls.getTop());
        System.out.println(ls.getTop());
        System.out.println(ls.pop());
        System.out.println(ls.pop());
        System.out.println("--------------------------------------------------");
        ListQueue<Integer> lq = new ListQueue<Integer>(Integer.class,5);
        lq.enQueue(2);
        lq.enQueue(3);
        lq.enQueue(4);
        lq.enQueue(5);
        lq.enQueue(6);
        System.out.println(lq.getHead());
        System.out.println(lq.deQueue());
        System.out.println(lq.deQueue());
        System.out.println(lq.getLength());
        System.out.println("-------------------------------------------------");
        LinkQueue<Integer> liq = new LinkQueue<Integer>();
        liq.enQueue(2);
        liq.enQueue(3);
        liq.enQueue(4);
        liq.enQueue(5);
        liq.enQueue(6);
        System.out.println(liq.getHead());
        System.out.println(liq.deQueue());
        System.out.println(liq.deQueue());
        System.out.println("-------------------------------------------------");
    }
}
