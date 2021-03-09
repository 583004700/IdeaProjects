package three.juc;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.locks.ReentrantLock;

public class AQSDemo {
    private Object tail = new Object();

    public static void main(String[] args) throws Exception {
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        Unsafe unsafe = (Unsafe) f.get(null);

        AQSDemo aqsDemo = new AQSDemo();
        long tailOffset = unsafe.objectFieldOffset(AQSDemo.class.getDeclaredField("tail"));

        Object o2 = new Object();

        //通过此方法可以直接改变对象的属性
        unsafe.compareAndSwapObject(aqsDemo, tailOffset, aqsDemo.tail, o2);

        System.out.println(aqsDemo.tail == o2);

        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        lock.unlock();
    }
}
