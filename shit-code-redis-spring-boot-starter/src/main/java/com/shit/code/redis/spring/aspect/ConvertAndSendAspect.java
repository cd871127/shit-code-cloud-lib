package com.shit.code.redis.spring.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author Anthony
 * @date 11/8/20
 **/
@Slf4j
@Aspect
public class ConvertAndSendAspect {

    @Pointcut("execution(public void org.springframework.data.redis.core.RedisTemplate.convertAndSend(java.lang.String,java.lang.Object))")
    public void convertAndSend() {
    }


    @Before("convertAndSend()")
    public void test(JoinPoint point) {
        log.info("===========123123123");
    }
}
