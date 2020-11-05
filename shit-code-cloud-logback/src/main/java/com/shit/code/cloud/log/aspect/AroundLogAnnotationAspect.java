package com.shit.code.cloud.log.aspect;

import com.shit.code.cloud.log.LogLevel;
import com.shit.code.cloud.log.annotation.AroundLog;
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
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class AroundLogAnnotationAspect extends AbstractAroundLogAspect {

    @Pointcut("@annotation(com.shit.code.cloud.log.annotation.AroundLog)")
    public void annotationLog() {
    }

    @Around("annotationLog()")
    public Object annotationLog(ProceedingJoinPoint joinPoint) throws Throwable {
        return proceed(joinPoint);
    }

    @Override
    protected LogLevel getLogLevel(ProceedingJoinPoint joinPoint, Method method) {
        return method.getAnnotation(AroundLog.class).level();
    }
}
