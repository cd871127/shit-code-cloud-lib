package com.shit.code.cache.spring;

import com.shit.code.cache.spring.evict.Sender;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.cache.Cache;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.transaction.AbstractTransactionSupportingCacheManager;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * @author Anthony
 * @date 11/7/20
 **/
@Setter
@Accessors(chain = true)
public class ShitCodeCacheManager extends AbstractTransactionSupportingCacheManager {

    private RedisCacheManager redisCacheManager;

    private CaffeineCacheManager caffeineCacheManager;

    private List<Sender> senders;

    @Override
    @NonNull
    protected Collection<? extends Cache> loadCaches() {
        return new HashSet<>();
    }

    @Override
    protected Cache getMissingCache(@NonNull String cacheName) {
        return new ShitCodeCache().setName(cacheName)
                .setCaffeineCache((CaffeineCache) caffeineCacheManager.getCache(cacheName))
                .setRedisCache((RedisCache) redisCacheManager.getCache(cacheName))
                .setSenders(senders);
    }

}
