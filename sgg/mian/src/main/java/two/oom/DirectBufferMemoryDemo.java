package two.oom;

import jdk.internal.misc.VM;

import java.nio.ByteBuffer;

/*
    Direct buffer memory

    JVM参数配置
    -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:MaxDirectMemorySize=5m

    导致原因:
    写NIO程序经 常使用ByteBuffer来读职或者写入数据，这是一种基 于通道(Channel)与缓冲区(Buffer)的I/0方式，
    这样能在- -些场景中显著提高性能，因为避免了在Java堆和Native堆中来回复制数据。
    这样能在- -些场景中显著提高性能，因为避免了在Java堆和Native堆中来回复制数据。
    ByteBuffer.allocate(capability) 第一 种方式是分配JVM堆内存，属于GC管辖范围， 由干需要拷贝所以速度相对较慢
    ByteBuffer.allocteDirect(capability)第2种方式是分配0S本地内存， 不属于GC 管辖范围，由于不需要内存拷贝所以速度相对较快。
    这时候堆内存充足，但本地内存可能已经使用光了，再次尝试分配本地内存就会出现OutOfMemoryError，那程序就直接崩溃了。
    它可以使用Native函数库直接分配堆外内存，然后通过- - -个存储在Java堆里面的DirectByteBuffer对象作为这块内存的引用进行操作。
 */
public class DirectBufferMemoryDemo {
    public static void main(String[] args) {
        System.out.println("配置的maxDirectMemory:" + VM.maxDirectMemory() / (double) 1024 / 1024 + "MB");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ByteBuffer bb = ByteBuffer.allocateDirect(6 * 1024 * 1024);
    }
}
