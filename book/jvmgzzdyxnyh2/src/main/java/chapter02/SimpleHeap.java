package chapter02;

public class SimpleHeap {
    private int id;
    public SimpleHeap(int id){
        this.id = id;
    }
    public void show(){
        System.out.println("My Id is "+id);
    }

    public static void main(String[] args) {
        SimpleHeap s1 = new SimpleHeap(1);
        SimpleHeap s2 = new SimpleHeap(2);
        s1.show();
        s2.show();
        // s1，s2 局部变量在栈中，指向堆中的 s1,s2 实例，方法区中保存 SimpleHeap 类及方法实现
    }
}
