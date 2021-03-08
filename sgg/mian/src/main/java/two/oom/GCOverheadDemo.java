package two.oom;

import java.util.ArrayList;
import java.util.List;

/*
    GC overhead limit exceeded

    GC回收时间过长会抛出 OutOfMemoryError。过长的定义是，超过98%的时间用来做GC并且回收了不到2%的堆内存
    连续多次GC都只收回了不到2%的极端情况下才会抛出。

    JVM参数配置
    -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:MaxDirectMemorySize=5m
 */
public class GCOverheadDemo {
    public static void main(String[] args) {
        int i = 0;
        List<String> list = new ArrayList<String>();
        try{
            while(true) {
                list.add(String.valueOf(++i).intern());
            }
        }catch (Throwable e){
            System.out.println("************************i"+i);
            e.printStackTrace();
        }
    }
}
