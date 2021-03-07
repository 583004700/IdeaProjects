package two.ref;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/*
    弱引用，垃圾回收运行了就没了
 */
public class WeakReferenceDemo {

    public static void main(String[] args) {
        Object o1 = new Object();
        WeakReference<Object> weakReference = new WeakReference<Object>(o1);
        System.out.println(o1);
        System.out.println(weakReference.get());
        o1 = null;
        System.gc();
        System.out.println("=========================");
        System.out.println(weakReference.get());
    }
}
