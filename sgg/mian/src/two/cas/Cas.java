package two.cas;

import java.util.concurrent.atomic.AtomicInteger;

public class Cas {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger();
        System.out.println(atomicInteger.compareAndSet(0,1));

        System.out.println(atomicInteger.compareAndSet(0,1));

    }
}
