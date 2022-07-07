package chapter02;

public class PermTest {
    public static void main(String[] args) {
        //在JDK 1.6、JDK 1.7中，方法区可以理解为永久区（Perm）。永久区可以使用参数- XX:PermSize和-XX:MaxPermSize指定，
        // 默认情况下，-XX:MaxPermSize为64MB。一个大 的永久区可以保存更多的类信息。如果系统使用了一些动态代理，
        // 那么有可能会在运行时 生成大量的类，这时就需要设置一个合理的永久区大小，确保不发生永久区内存溢出。
        // 在JDK 1.8、JDK1.9、JDK1.10中，永久区已经被彻底移除。取而代之的是元数据区，
        // 元数据区大小可以使用参数-XX:MaxMetaspaceSize指定（一个大的元数据区可以使系统支 持更多的类），
        // 这是一块堆外的直接内存。与永久区不同，如果不指定大小，默认情况 下，虚拟机会耗尽所有的可用系统内存。
        // 图2.12显示了JDK 1.8中的元数据区，JDK 1.9、 JDK 1.10与此相同，不再赘述。
    }
}
