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
 * @author anthonychen
 * @date 2020/11/3
 */
@Aspect
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestMappingAnnotationLogAspect extends AbstractAroundLogAspect {

    private AroundLogProperties aroundLogProperties;

    public RequestMappingAnnotationLogAspect(AroundLogProperties aroundLogProperties) {
        this.aroundLogProperties = aroundLogProperties;
    }


    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)" +
            "||@annotation(org.springframework.web.bind.annotation.PostMapping)" +
            "||@annotation(org.springframework.web.bind.annotation.PatchMapping)" +
            "||@annotation(org.springframework.web.bind.annotation.PutMapping)" +
            "||@annotation(org.springframework.web.bind.annotation.RequestMapping)" +
            "||@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public void requestMappingLog() {
    }


    @Around("requestMappingLog()")
    public Object requestMappingLog(ProceedingJoinPoint joinPoint) throws Throwable {
        return proceed(joinPoint);
    }

    @Override
    protected LogLevel getLogLevel(ProceedingJoinPoint joinPoint, Method method) {
        return aroundLogProperties.getRequestMapping().getLevel();
    }
}
