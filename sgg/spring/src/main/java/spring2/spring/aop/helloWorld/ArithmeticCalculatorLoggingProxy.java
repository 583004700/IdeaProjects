package spring2.spring.aop.helloWorld;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ArithmeticCalculatorLoggingProxy implements InvocationHandler {
    private Object target;

    public ArithmeticCalculatorLoggingProxy(Object target){
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(method.getName()+"before");
        Object result = method.invoke(target,args);
        System.out.println(method.getName()+"after");
        return result;
    }

    public Object getProxy(){
        ArithmeticCalculator arithmeticCalculatorProxy = (ArithmeticCalculator) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
        return arithmeticCalculatorProxy;
    }

}
