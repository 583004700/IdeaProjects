package spring2.spring.aop.impl;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * 可以使用 @Order 注解指定切面优先级，值越小优先级越高
 */
@Order(2)
@Aspect
@Component
public class LoggingAspect {

    /**
     * 定义一个方法，用于声明切入点表达式，可以被引用，也可以在其它类引用
     */
    @Pointcut("execution(public int spring2.spring.aop.impl.ArithmeticCalculator.*(..))")
    public void declareJoinPointExpression(){

    }


    //前置通知
    @Before("declareJoinPointExpression()")
    public void beforeMethod(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        List<Object> args = Arrays.asList(joinPoint.getArgs());
        System.out.println("The method "+methodName+" begins with " + args);
    }

    //后置通知：在目标方法执行后（无论是否发生异常），执行的通知
    //在后置通知中还不能访问目标方法执行的结果。
    @After("execution(* spring2.spring.aop.impl.*.*(int,int))")
    public void afterMethod(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        System.out.println("The method "+methodName+" ends ");
    }

    /**
     * 在方法正常结束后执行的代码
     * 返回通知是可以取到方法的返回值的
     * @param joinPoint
     */
    @AfterReturning(value="execution(public int spring2.spring.aop.impl.ArithmeticCalculator.*(..))",
    returning = "result")
    public void afterReturning(JoinPoint joinPoint,Object result){
        String methodName = joinPoint.getSignature().getName();
        System.out.println("The method "+methodName+" ends with " + result);
    }

    /**
     * 在目标方法出现异常时执行
     * @param joinPoint
     * @param ex 也可以指定其它的异常，不一定是Exception
     */
    @AfterThrowing(value = "execution(* spring2.spring.aop.impl.*.*(int,int))",
    throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint,Exception ex){
        String methodName = joinPoint.getSignature().getName();
        System.out.println("The method "+methodName+" occurs exception: " + ex);
    }

    /**
     * 可以决定方法是否执行，返回值
     * @param pjp
     */
    @Around("execution(public int spring2.spring.aop.impl.ArithmeticCalculator.*(..))")
    public Object aroundMethod(ProceedingJoinPoint pjp){
        String methodName = pjp.getSignature().getName();
        Object result = null;
        try {
            System.out.println("前置通知");
            result = pjp.proceed();
            System.out.println("返回通知");
        } catch (Throwable throwable) {
            System.out.println("异常通知");
            throwable.printStackTrace();
        }finally {
            System.out.println("后置通知");
        }
        return result;
    }
}
