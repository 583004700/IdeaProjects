package two.oom;

/*
        高并发请求服务器时,经常出现如下异常:java. lang. OutOfMemoryError: unable to create new native thread
        准确的讲该native thread异 常与对应的平台有关
        导致原因:
        你的应用创建了太多线程了,一个应用进程创建多个线程，超过系统承戴极限
        你的服务器并不允许你的应用程序创建这么多线程, linux.系统默认允许单个进程可l以创建的线程数是1024个,
        你的应用创建超过这个数量,就会报java. lang. OutOfMemoryError: unable to create new native thread
        解决办法:
        1.想办法降低你应用程序创建线程的数量,分析应用是否真的需要创建这么多线程,如果不是,改代码将线程数降到最低
        2.对于有的应用,磅实需要创建很多线程远超过L inux系统的默认1024个线程的限制,可以通过修改L inux服务器配置,扩大l inux默认限制
 */
public class UnableCreateNewThreadDemo {
    public static void main(String[] args) {
        for (int i = 0; ; i++) {
            System.out.println("i="+i);
            new Thread(()->{
                try{
                    Thread.sleep(Integer.MAX_VALUE);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            },""+i).start();
        }
    }
}
