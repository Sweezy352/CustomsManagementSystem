package com.example.sweezcustoms.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public abstract class BaseAspect {
    @Pointcut("@within(org.springframework.stereotype.Service)")
    public void isServiceLayer(){
    }

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void isControllerLayer(){}

    @Pointcut("target(com.example.sweezcustoms.repository.*)")
    public void isRepositoryLayer(){}

    @Pointcut("isServiceLayer() || isControllerLayer() || isRepositoryLayer()")
    public void checkEveryLayer(){}
}
