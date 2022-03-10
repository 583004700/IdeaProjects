package chapter2;

public class Test {
    public static void main(String[] args) {
        Link<Integer> link = new Link<Integer>();
        link.addLast(1);
        link.addLast(2);
        link.addFirst(3);
        link.add(1,4);
        System.out.println(link);
        link.remove(3);
        System.out.println(link);
        link.addLast(10);
        System.out.println(link);
        System.out.println("-----------------------------------------------");

        DoubleLink<Integer> doubleLink = new DoubleLink<Integer>();
        doubleLink.addLast(1);
        doubleLink.addLast(2);
        doubleLink.addFirst(3);
        doubleLink.add(1,4);
        System.out.println(doubleLink);
        doubleLink.remove(3);
        System.out.println(doubleLink);
        doubleLink.addLast(10);
        System.out.println(doubleLink);
        System.out.println("-----------------------------------------------");
    }
}
