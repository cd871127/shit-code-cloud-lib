package com.shit.code.cloud.mybatis.cache;

import com.shit.code.spring.utils.SpringContextUtil;
import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;


/**
 * https://www.cnblogs.com/cxuanBlog/p/11333021.html
 */
public class RedisCache implements Cache {
    private BoundHashOperations<String, String, Object> boundHashOperations = null;
    private final String id;

    public RedisCache(String id) {
        this.id = id;
    }

    private BoundHashOperations<String, String, Object> getBoundHashOperations() {
        if (boundHashOperations == null) {
            bindKey();
        }
        return boundHashOperations;
    }

    @SuppressWarnings("unchecked")
    private void bindKey() {
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
        getBoundHashOperations().put(key.toString(), value);
    }

    @Override
    public Object getObject(Object key) {
        return getBoundHashOperations().get(key.toString());
    }

    @Override
    public Object removeObject(Object key) {
        Object object = getObject(key);
        getBoundHashOperations().delete(key.toString());
        return object;
    }

    @Override
    public void clear() {
        getBoundHashOperations().expire(Duration.ZERO);
    }

    @Override
    public int getSize() {
        Long size = getBoundHashOperations().size();
        return size == null ? 0 : Math.toIntExact(size);
    }
}
