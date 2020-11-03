package com.shit.code.cloud.log.aspect;

import com.shit.code.cloud.log.LogLevel;
import com.shit.code.cloud.log.autoconfigure.AroundLogProperties;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Method;

/**
 * @author Anthony
 * @date 11/3/20
 **/
@Aspect
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MethodAroundLogAspect extends AbstractAroundLogAspect {

    private AroundLogProperties aroundLogProperties;

    public MethodAroundLogAspect(AroundLogProperties aroundLogProperties) {
        this.aroundLogProperties = aroundLogProperties;
    }


    @Pointcut("execution(* com.shit.code.cloud.infrastructure..*.*(..))&&!within(com.sun..*)")
    public void publicMethodLog() {
    }


    @Around("publicMethodLog()")
    public Object publicMethodLog(ProceedingJoinPoint joinPoint) throws Throwable {
        return proceed(joinPoint);
    }

    @Override
    protected LogLevel getLogLevel(ProceedingJoinPoint joinPoint, Method method) {
        return aroundLogProperties.getMethod().getLevel();
    }
}
