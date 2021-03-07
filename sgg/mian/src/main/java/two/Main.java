package two;


import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);

        Callable callable = new Callable() {
            @Override
            public Integer call() throws Exception {
                System.out.println("执行任务");
                TimeUnit.SECONDS.sleep(5);
                return 1;
            }
        };

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    ScheduledFuture<Integer> result = scheduledExecutorService.schedule(callable,0, TimeUnit.SECONDS);
                    System.out.println("提交任务");
                    Integer r = result.get();
                    System.out.println("执行结果为："+r);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        scheduledExecutorService.scheduleAtFixedRate(runnable,0,2,TimeUnit.SECONDS);

    }
}
