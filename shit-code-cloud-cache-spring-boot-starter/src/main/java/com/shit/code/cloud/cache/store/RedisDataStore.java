package com.shit.code.cloud.cache.store;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

@Slf4j
public class RedisDataStore implements DataStore {

    private RedisTemplate<Object, Object> redisTemplate;

    private String KEY_PREFIX;

    public RedisDataStore(RedisTemplate<Object, Object> redisTemplate) {
        KEY_PREFIX = "MULTI_LEVEL_CACHE_";
        this.redisTemplate = redisTemplate;
    }

    public RedisDataStore(RedisTemplate<Object, Object> redisTemplate, String keyPrefix) {
        this(redisTemplate);
        if (StringUtils.isNotEmpty(keyPrefix)) {
            this.KEY_PREFIX = keyPrefix;
        }
    }

    @Override
    public Object get(Object key) {
        log.info("redis get:{}", key);
        return redisTemplate.boundValueOps(realKey(key)).get();
    }

    @Override
    public void put(Object key, DataEntity dataEntity) {
        log.info("redis put:{}", key);
        redisTemplate.boundValueOps(realKey(key)).set(dataEntity.getValue(), dataEntity.getCacheConfig().getTtl(), TimeUnit.MILLISECONDS);
    }

    @Override
    public void remove(Object key) {
        redisTemplate.delete(key);
    }

    @Override
    public void clear() {
        //扫描所有的key前缀, 然后删除
    }

    /**
     * 获取真实的rediskey
     *
     * @return
     */
    private Object realKey(Object key) {
        return KEY_PREFIX + key.toString();
    }
}
