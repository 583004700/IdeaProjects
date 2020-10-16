package spring2.spring.aop.helloWorld;

public class Main {

    public static void main(String[] args) {
        ArithmeticCalculator arithmeticCalculator = null;
        arithmeticCalculator = new ArithmeticCalculatorImpl();
//
//        int result = arithmeticCalculator.add(1, 2);
//        System.out.println(result);
//        result = arithmeticCalculator.sub(4, 2);
//        System.out.println(result);

        ArithmeticCalculatorLoggingProxy arithmeticCalculatorLoggingProxy = new ArithmeticCalculatorLoggingProxy(arithmeticCalculator);
        ArithmeticCalculator ac = ((ArithmeticCalculator)arithmeticCalculatorLoggingProxy.getProxy());

        Runnable r = new Runnable() {
            @Override
            public void run() {
                ac.add(5,6);
            }
        };
        for(int i=0;i<10;i++){
            Thread t1 = new Thread(r);
            t1.start();
        }

        //System.out.println(result);
    }

}
