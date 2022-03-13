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
    }
}
