package com.shit.code.cloud.cache.annotation;

import org.springframework.cache.annotation.CachePut;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

import static com.shit.code.cloud.cache.MultiLevelCache.DEFAULT_NAME;

/**
 * @author Anthony
 * @date 10/1/20
 **/
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@CachePut(DEFAULT_NAME)
public @interface MultiLevelCachePut {


    @AliasFor("cacheNames")
    String[] value() default {DEFAULT_NAME};


    @AliasFor("value")
    String[] cacheNames() default {DEFAULT_NAME};


    String key() default "";


    String keyGenerator() default "";


    String cacheManager() default "";


    String cacheResolver() default "";


    String condition() default "";


    String unless() default "";


    long ttl() default 3000L;

}
