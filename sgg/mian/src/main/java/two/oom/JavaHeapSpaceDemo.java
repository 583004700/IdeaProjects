package two.oom;

/*
    Java heap space

    JVM参数配置
    -Xms10m -Xmx10m
 */

public class JavaHeapSpaceDemo {
    public static void main(String[] args) {
        byte[] bytes = new byte[1024 * 1024 * 1024];
    }
}
