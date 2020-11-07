package com.shit.code.redis.spring.message;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

/**
 * @author Anthony
 * @date 11/7/20
 **/
@Target({TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface RedisMessageListener {

    @AliasFor("channels")
    String[] value() default {};

    @AliasFor("value")
    String[] channels() default {};
}
