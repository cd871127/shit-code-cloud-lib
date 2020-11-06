package com.shit.code.spring.boot.cache;

import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.cache.Cache;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.transaction.AbstractTransactionSupportingCacheManager;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author Anthony
 * @date 11/7/20
 **/
@Setter
@Accessors(chain = true)
public class ShitCodeCacheManager extends AbstractTransactionSupportingCacheManager {

    private RedisCacheManager redisCacheManager;

    private CaffeineCacheManager caffeineCacheManager;


    @Override
    protected Collection<? extends Cache> loadCaches() {
        return new HashSet<>();
    }

    @Override
    protected Cache getMissingCache(@NonNull String cacheName) {
        return new ShitCodeCache().setName(cacheName)
                .setCaffeineCache((CaffeineCache) caffeineCacheManager.getCache(cacheName))
                .setRedisCache((RedisCache) redisCacheManager.getCache(cacheName));
    }

}
