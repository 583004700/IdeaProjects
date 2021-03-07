package two.ref;

/*
    强引用
 */
public class StrongReferenceDemo {
    public static void main(String[] args) {
        Object obj1 = new Object();
        Object obj2 = obj1;
        obj1 = null;
        System.gc();
        //obj2引用的对象还存在
        System.out.println(obj2);
    }
}
