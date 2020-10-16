package spring2.spring.aop.helloWorld;

public class ArithmeticCalculatorImpl implements ArithmeticCalculator {
    @Override
    public synchronized int add(int i, int j) {
        System.out.println(Thread.currentThread()+"方法运行开始");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread()+"方法运行结束");
        return i + j;
    }

    @Override
    public int sub(int i, int j) {
        return i - j;
    }

    @Override
    public int mul(int i, int j) {
        return i * j;
    }

    @Override
    public int div(int i, int j) {
        return i / j;
    }
}
