package two.ref;

import java.lang.ref.SoftReference;

/*
    软引用，内存不够时才回收对象
 */
public class SoftReferenceDemo {
    /*
        内存够用的时候就保留，不够用就回收！
     */
    public static void softRefMemoryEnough() {
        Object o1 = new Object();
        SoftReference<Object> softReference = new SoftReference<Object>(o1);
        System.out.println(o1);
        System.out.println(softReference.get());

        o1 = null;
        System.gc();
        System.out.println(softReference.get());
    }

    /*
        jvm配置，故意产生大对象并配置小的内存，让它内存不够用了导致OOM，看软引用的回收情况
        -Xms5m -Xmx5m -XX:+PrintGCDetails
     */
    public static void softRefMemoryNotEnough() {
        Object o1 = new Object();
        SoftReference<Object> softReference = new SoftReference<Object>(o1);
        System.out.println(o1);
        System.out.println(softReference.get());

        o1 = null;

        try {
            byte[] bytes = new byte[30 * 1024 * 1024];
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            System.out.println(softReference.get());
        }
    }

    public static void main(String[] args) {
        //softRefMemoryEnough();
        softRefMemoryNotEnough();
    }
}
