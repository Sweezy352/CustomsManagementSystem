package com.example.sweezcustoms.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class LoggerAspect {
    @Pointcut("@within(org.springframework.stereotype.Service)")
    public void isServiceLayer(){
    }


    @Before("isServiceLayer() ")
    public void logLayerBefore(JoinPoint joinPoint){
        log.info("Before invoke method: {}, with args: {} ", joinPoint.getSignature().getName(), joinPoint.getArgs());
    }

    @AfterReturning(value = "isServiceLayer()", returning = "result")
    public void logLayerAfterReturning(Object result, JoinPoint joinPoint){
        log.info("AfterReturning invoke method: " + joinPoint.getSignature().getName() + " with result: " + result);
    }

    @AfterThrowing(value = "isServiceLayer()", throwing = "ex")
    public void logLayerAfterThrowing(Throwable ex, JoinPoint joinPoint){
        log.info("AfterThrowing invoke method: " + joinPoint.getSignature().getName() + " with exception: " + ex.getMessage());
    }

    @After(value = "isServiceLayer()")
    public void logLayerAfter(JoinPoint joinPoint){
        log.info("After invoke method: " + joinPoint.getSignature().getName());
    }
}

