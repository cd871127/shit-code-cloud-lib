package com.shit.code.cloud.redis;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * redis初始化
 */
public class RedisInitializer implements BeanPostProcessor {

    @Override
    @SuppressWarnings("unchecked")
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().equals(RedisTemplate.class)) {
            RedisTemplate<Object, Object> redisTemplate = (RedisTemplate<Object, Object>) bean;
            redisTemplate.setKeySerializer(RedisSerializer.string());
            redisTemplate.setStringSerializer(RedisSerializer.string());
            redisTemplate.setHashKeySerializer(RedisSerializer.string());
            redisTemplate.setValueSerializer(RedisSerializer.json());
            redisTemplate.setHashValueSerializer(RedisSerializer.json());
        }
        return bean;
    }

}
