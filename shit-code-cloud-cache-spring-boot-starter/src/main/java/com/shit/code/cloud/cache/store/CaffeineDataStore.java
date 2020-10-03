package com.shit.code.cloud.cache.store;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Scheduler;
import com.shit.code.cloud.cache.CacheConfig;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * @author Anthony
 * @date 10/1/20
 **/
@Slf4j
public class CaffeineDataStore implements DataStore {

    /**
     * 不同时长的缓存
     */
    private final ConcurrentMap<Long, Cache<Object, Object>> cacheMap = new ConcurrentHashMap<>(16);

    private final Cache<Object, CacheConfig> cacheConfigCache = Caffeine.newBuilder()
            .build();

    public CaffeineDataStore() {
    }

    @Override
    public Object get(Object key) {
        CacheConfig cacheConfig = cacheConfigCache.getIfPresent(key);
        if (cacheConfig == null) {
            return null;
        }

        Cache<Object, Object> cache = cacheMap.get(cacheConfig.getTtl());
        if (cache == null) {
            return null;
        }

        return cache.getIfPresent(key);
    }

    @Override
    public void put(Object key, DataEntity dataEntity) {
        Long ttl = dataEntity.getCacheConfig().getTtl();
        Cache<Object, Object> cache = cacheMap.computeIfAbsent(ttl,
                newTtlKey -> Caffeine.newBuilder()
                        .expireAfterWrite(newTtlKey, TimeUnit.MILLISECONDS)
                        .maximumSize(100)
                        .scheduler(Scheduler.systemScheduler())
                        .removalListener((k, v, c) -> {
                            log.info("key:{},value{},被移除：{}", k, v, c);
                            if (c.wasEvicted()) {
                                if (k != null) {
                                    cacheConfigCache.invalidate(k);
                                }
                            }
                        })
                        .build());
        cache.put(key, dataEntity.getValue());
        cacheConfigCache.put(key, dataEntity.getCacheConfig());
    }

    @Override
    public void remove(Object key) {
        CacheConfig cacheConfig = cacheConfigCache.getIfPresent(key);
        if (cacheConfig == null) {
            return;
        }
        Cache<Object, Object> cache = cacheMap.get(cacheConfig.getTtl());
        if (cache == null) {
            return;
        }
        cache.invalidate(key);
    }

    @Override
    public void clear() {
        cacheMap.clear();
        cacheConfigCache.invalidateAll();
    }


}
