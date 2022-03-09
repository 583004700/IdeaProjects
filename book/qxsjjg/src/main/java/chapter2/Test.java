package chapter2;

public class Test {
    public static void main(String[] args) {
        Link<Integer> link = new Link<Integer>();
        link.addLast(1);
        link.addLast(2);
        link.addFirst(3);
        link.add(1,4);
        System.out.println(link);
        link.remove(2);
        System.out.println(link);

        System.out.println("-----------------------------------------------");
    }
}
