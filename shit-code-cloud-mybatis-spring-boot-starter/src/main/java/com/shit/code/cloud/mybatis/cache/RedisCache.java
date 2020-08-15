package com.shit.code.cloud.mybatis.cache;

import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import shit.code.cloud.spring.utils.SpringContextUtil;

import java.time.Duration;


/**
 * https://www.cnblogs.com/cxuanBlog/p/11333021.html
 */
public class RedisCache implements Cache {
    BoundHashOperations<String, String, Object> boundHashOperations;
    private final String id;

    @SuppressWarnings("unchecked")
    public RedisCache(String id) {
        this.id = id;
        RedisTemplate<String, Object> redisTemplate =
                (RedisTemplate<String, Object>) SpringContextUtil.getContext().getBean("redisTemplate", RedisTemplate.class);
        this.boundHashOperations = redisTemplate.boundHashOps(id);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void putObject(Object key, Object value) {
        boundHashOperations.put(key.toString(), value);
    }

    @Override
    public Object getObject(Object key) {
        return boundHashOperations.get(key.toString());
    }

    @Override
    public Object removeObject(Object key) {
        Object object = getObject(key);
        boundHashOperations.delete(key.toString());
        return object;
    }

    @Override
    public void clear() {
        boundHashOperations.expire(Duration.ZERO);
    }

    @Override
    public int getSize() {
        Long size = boundHashOperations.size();
        return size == null ? 0 : Math.toIntExact(size);
    }
}
