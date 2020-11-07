package com.shit.code.cache.spring;

import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.AbstractValueAdaptingCache;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.lang.NonNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * @author Anthony
 * @date 11/7/20
 **/
@Setter
@Accessors(chain = true)
@Slf4j
public class ShitCodeCache extends AbstractValueAdaptingCache {

    private String name;

    private RedisCache redisCache;

    private CaffeineCache caffeineCache;

    protected ShitCodeCache() {
        super(true);
    }

    public ShitCodeCache setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    protected Object lookup(@NonNull Object key) {
        Object result = caffeineCache.getNativeCache().getIfPresent(key);
        if (result != null) {
            log.debug("key:{},命中一级缓存", key);
            return result;
        }
        try {
            Method method = redisCache.getClass().getDeclaredMethod("lookup", Object.class);
            method.setAccessible(true);
            result = method.invoke(redisCache, key);
            if (result != null) {
                log.debug("key:{},命中二级缓存", key);
                caffeineCache.put(key, result);
                return result;
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            log.warn("key:{}, 读取二级缓存异常 {}", key, ExceptionUtils.getStackTrace(e));
        }
        log.debug("key:{},未命中缓存", key);
        return null;
    }

    @Override
    @NonNull
    public String getName() {
        return name;
    }

    @Override
    @NonNull
    public Object getNativeCache() {
        return redisCache;
    }

    @Override
    public <T> T get(@NonNull Object key, @NonNull Callable<T> valueLoader) {
        T result = caffeineCache.get(key, valueLoader);
        if (result != null) {
            log.debug("key:{},命中一级缓存", key);
            return result;
        }
        result = redisCache.get(key, valueLoader);
        if (result != null) {
            log.debug("key:{},命中二级缓存", key);
            caffeineCache.put(key, result);
            return result;
        }
        log.debug("key:{},未命中缓存", key);
        return null;
    }

    @Override
    public void put(@NonNull Object key, Object value) {
        redisCache.put(key, value);
        log.debug("key:{},设置二级缓存成功", key);
        caffeineCache.put(key, value);
        log.debug("key:{},设置一级缓存成功", key);
    }

    @Override
    public void evict(@NonNull Object key) {
        caffeineCache.evict(key);
        log.debug("key:{},清理一级缓存成功", key);
        redisCache.evict(key);
        log.debug("key:{},清理二级缓存成功", key);
    }

    @Override
    public void clear() {
        caffeineCache.clear();
        log.debug("删除一级缓存成功");
        redisCache.clear();
        log.debug("删除二级缓存成功");
    }
}
