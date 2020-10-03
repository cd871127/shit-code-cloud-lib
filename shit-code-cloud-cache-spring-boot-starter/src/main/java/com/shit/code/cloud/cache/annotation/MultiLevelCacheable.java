package com.shit.code.cloud.cache.annotation;

import org.springframework.cache.annotation.Cacheable;
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
@Cacheable(DEFAULT_NAME)
public @interface MultiLevelCacheable {

    @AliasFor("cacheNames")
    String[] value() default {DEFAULT_NAME};


    @AliasFor("value")
    String[] cacheNames() default {DEFAULT_NAME};


    String key() default "";


    String keyGenerator() default "";


    String cacheManager() default "";


    String cacheResolver() default "multiLevelCacheResolver";


    String condition() default "";


    String unless() default "";


    boolean sync() default false;


    long ttl() default 3000L;

    /**
     * 序列化读操作的锁时长
     */
    long lockMillis() default 0L;

    /**
     * 多少毫秒没有获取锁就读db,自旋时长
     */
    long spanMillis() default 0L;
}
