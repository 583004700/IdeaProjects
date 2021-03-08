package two.ref;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

public class PhantomReferenceDemo {
    public static void main(String[] args) throws Exception{
        Object o1 = new Object();
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<Object>();
        PhantomReference<Object> phantomReference = new PhantomReference<Object>(o1, referenceQueue);
        o1 = null;
        System.gc();
        Thread.sleep(500);
        Reference<?> reference = referenceQueue.poll();
        o1 = reference.get();
        System.out.println(o1);
    }
}
