package spring2.spring.aop.helloWorld;

public class Main {

    public static void main(String[] args) {
        ArithmeticCalculator arithmeticCalculator = null;
        arithmeticCalculator = new ArithmeticCalculatorImpl();

        int result = arithmeticCalculator.add(1, 2);
        System.out.println(result);
        result = arithmeticCalculator.sub(4, 2);
        System.out.println(result);

        ArithmeticCalculatorLoggingProxy arithmeticCalculatorLoggingProxy = new ArithmeticCalculatorLoggingProxy(arithmeticCalculator);
        result = ((ArithmeticCalculator)arithmeticCalculatorLoggingProxy.getProxy()).add(5,5);
        System.out.println(result);
    }

}
