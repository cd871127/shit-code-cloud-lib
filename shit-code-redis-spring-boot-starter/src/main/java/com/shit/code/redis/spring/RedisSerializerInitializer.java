package com.shit.code.redis.spring;

import com.shit.code.redis.spring.serializer.DefaultJsonSerializerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.lang.NonNull;

/**
 * redis初始化
 */
@Slf4j
public class RedisSerializerInitializer implements BeanPostProcessor {

    @Override
    @SuppressWarnings("unchecked")
    public Object postProcessAfterInitialization(Object bean, @NonNull String beanName) throws BeansException {
        if (bean.getClass().equals(RedisTemplate.class)) {
            log.info("设置Redis序列化器");
            RedisTemplate<Object, Object> redisTemplate = (RedisTemplate<Object, Object>) bean;
            redisTemplate.setKeySerializer(RedisSerializer.string());
            redisTemplate.setStringSerializer(RedisSerializer.string());
            redisTemplate.setHashKeySerializer(RedisSerializer.string());

            redisTemplate.setValueSerializer(DefaultJsonSerializerFactory.getSerializer());
            redisTemplate.setHashValueSerializer(DefaultJsonSerializerFactory.getSerializer());
        }
        return bean;
    }
}
