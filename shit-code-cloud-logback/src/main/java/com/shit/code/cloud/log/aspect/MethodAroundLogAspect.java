package com.shit.code.cloud.log.aspect;

import com.shit.code.cloud.log.LogLevel;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author Anthony
 * @date 11/3/20
 **/
@Aspect
@Slf4j
public class MethodAroundLogAspect extends AbstractAroundLogAspect {

    private final LogLevel level;

    public MethodAroundLogAspect(LogLevel level) {
        this.level = level;
    }


    @Pointcut("execution(* com.shit.code.cloud.infrastructure..*.*(..))")
    public void publicMethodLog() {
    }


    @Around("publicMethodLog()")
    public Object publicMethodLog(ProceedingJoinPoint joinPoint) throws Throwable {
        return proceed(joinPoint);
    }

    @Override
    protected LogLevel getLogLevel(ProceedingJoinPoint joinPoint) {
        return level;
    }
}
