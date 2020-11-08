package com.shit.code.cache.spring.evict.redis;

import com.shit.code.cache.spring.ServerInfo;
import com.shit.code.cache.spring.ShitCodeCache;
import com.shit.code.cache.spring.ShitCodeCacheManager;
import com.shit.code.cache.spring.evict.EvictInfo;
import com.shit.code.cache.spring.evict.Receiver;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

/**
 * @author Anthony
 * @date 11/9/20
 **/
@Slf4j
public class DefaultReceiver implements Receiver {

    public DefaultReceiver(ShitCodeCacheManager shitCodeCacheManager) {
        this.shitCodeCacheManager = shitCodeCacheManager;
    }

    private final ShitCodeCacheManager shitCodeCacheManager;

    @Override
    public void receive(EvictInfo evictInfo) {
        ServerInfo serverInfo = evictInfo.getFrom();
        if (ServerInfo.INSTANCE.equals(serverInfo)) {
            return;
        }
        log.info("收到Redis淘汰缓存的通知:{}", evictInfo);

        Collection<String> cacheNames;
        if (StringUtils.isEmpty(evictInfo.getCacheName())) {
            //all cache need evict
            log.debug("cacheName为空,取所有的缓存");
            cacheNames = shitCodeCacheManager.getCacheNames();
        } else {
            cacheNames = Collections.singleton(evictInfo.getCacheName());
        }
        cacheNames.stream().map(shitCodeCacheManager::getCache)
                .filter(Objects::nonNull)
                .forEach(cache -> {
                    if (cache instanceof ShitCodeCache) {
                        if (evictInfo.getKey() == null) {
                            ((ShitCodeCache) cache).clearNoSend();
                        } else {
                            ((ShitCodeCache) cache).evictNoSend(evictInfo.getKey());
                        }
                    } else {
                        if (evictInfo.getKey() == null) {
                            cache.clear();
                        } else {
                            cache.evict(evictInfo.getKey());
                        }
                    }
                });
    }
}
