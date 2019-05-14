package org.light4j.sample.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.light4j.sample.annotation.LogAction;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class LogAspect {

    @Pointcut("@annotation(org.light4j.sample.annotation.LogAction)")
    public void log() {

    }

    /**
     * 前置通知
     */
    @Before("log()")
    public void doBeforeController(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LogAction action = method.getAnnotation(LogAction.class);
        System.out.println("action名称: " + action.value());
    }

    /**
     * 后置通知
     */
    @AfterReturning(pointcut = "log()", returning = "retValue")
    public void doAfterController(JoinPoint joinPoint, Object retValue) {
        System.out.println("ret value is :" + retValue);
    }
}
