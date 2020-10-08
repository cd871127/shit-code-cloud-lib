package com.shit.code.cloud.cache;

import com.shit.code.cloud.cache.annotation.MultiLevelCachePut;
import com.shit.code.cloud.cache.annotation.MultiLevelCacheable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.interceptor.AbstractCacheResolver;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.lang.NonNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Anthony
 * @date 10/1/20
 **/
@Slf4j
public class MultiLevelCacheResolver extends AbstractCacheResolver {

    private static final ConcurrentMap<Method, CacheConfig> CACHE_CONFIG_MAP = new ConcurrentHashMap<>(16);

    public MultiLevelCacheResolver(MultiLevelCacheManager multiLevelCacheManager) {
        super(multiLevelCacheManager);
    }

    /**
     * 获取所有的cache名字
     *
     * @param context
     * @return
     */
    @Override
    protected Collection<String> getCacheNames(@NonNull CacheOperationInvocationContext<?> context) {
        Set<String> cacheNames = context.getOperation().getCacheNames();
        Method method = context.getMethod();
        CacheConfig cacheConfig = getCacheConfig(method);
        //每个缓存设置配置
        cacheNames.forEach(name -> setCacheConfig(name, cacheConfig));
        return cacheNames;
    }

    /**
     * 从方法注解中获取缓存的配置
     *
     * @param method
     * @return
     */
    private CacheConfig getCacheConfig(Method method) {
        if (CACHE_CONFIG_MAP.containsKey(method)) {
            return CACHE_CONFIG_MAP.get(method);
        }
        //获取方法返回类型
        Type returnType = method.getGenericReturnType();
        Annotation[] annotations = method.getAnnotations();
        CacheConfig cacheConfig = new CacheConfig().setType(returnType);
        Arrays.stream(annotations).forEach(annotation -> {
            //如果存在MultiLevelCachePut注解，获取ttl
            if (annotation instanceof MultiLevelCachePut) {
                MultiLevelCachePut multiLevelCachePut = (MultiLevelCachePut) annotation;
                cacheConfig.setTtl(multiLevelCachePut.ttl());
            }
            //如果存在MultiLevelCachePut注解，获取ttl
            if (annotation instanceof MultiLevelCacheable) {
                MultiLevelCacheable multiLevelCacheable = (MultiLevelCacheable) annotation;
                cacheConfig.setTtl(multiLevelCacheable.ttl());
                if (multiLevelCacheable.sync()) {
                    if (multiLevelCacheable.lockMillis() <= 0 || multiLevelCacheable.spanMillis() <= 0) {
                        throw new IllegalArgumentException("MultiLevelCacheable注解sync为true时,必须设置lockMillis和spanMillis");
                    }
                    cacheConfig.setLockMillis(multiLevelCacheable.lockMillis());
                    cacheConfig.setSpanMillis(multiLevelCacheable.spanMillis());
                }
            }
        });
        //缓存缓存的配置
        CACHE_CONFIG_MAP.put(method, cacheConfig);
        return cacheConfig;
    }

    /**
     * 设置缓存的配置
     *
     * @param name
     * @param cacheConfig
     */
    public void setCacheConfig(String name, CacheConfig cacheConfig) {
        MultiLevelCache multiLevelCache = ((MultiLevelCache) getCacheManager().getCache(name));
        if (multiLevelCache != null) {
            multiLevelCache.setCacheConfig(cacheConfig);
        }
    }
}
