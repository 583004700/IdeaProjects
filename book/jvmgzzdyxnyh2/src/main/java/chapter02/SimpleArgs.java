package chapter02;

public class SimpleArgs {
    public static void main(String[] args) {
        for (int i = 0; i < args.length; i++) {
            System.out.println("参数"+(i+1)+":"+args[i]);
        }
        // 打印最大堆内存
        System.out.println("Xmx:"+Runtime.getRuntime().maxMemory()/1024/1024+"M");
        // java -Xmx32m 类名 args参数
    }
}
