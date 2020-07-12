package com.atguigu.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Arrays;

@Aspect
public class LogAspects {

    //抽取公共的切入点表达式
    //1、本类引用
    //2、其他的切面引用，则要使用全名
    @Pointcut("execution(public int com.atguigu.aop.MathCalculator.*(..))")
    public void pointCut(){}

    //public int com.atguigu.aop.MathCalculator.*(int, int)
    @Before("pointCut()")
    public void logStart(JoinPoint joinPoint){
        System.out.println(joinPoint.getSignature().getName()+"运行。。。参数列表是：{"+ Arrays.asList(joinPoint.getArgs())+"}");
    }

    @After("com.atguigu.aop.LogAspects.pointCut()")
    public void logEnd(){
        System.out.println("方法结束。。。");
    }
    //如果有多个参数，JoinPoint必须写在第一个参数位置
    @AfterReturning(value="pointCut()",returning = "result")
    public void logReturn(JoinPoint joinPoint,Object result){
        System.out.println(joinPoint.getSignature().getName()+"正常返回。。。运行结果：{"+result+"}");
    }
    @AfterThrowing(value = "pointCut()",throwing = "exception")
    public void logException(Exception exception){
        System.out.println("方法异常。。。异常信息：{"+exception+"}");
    }
}
