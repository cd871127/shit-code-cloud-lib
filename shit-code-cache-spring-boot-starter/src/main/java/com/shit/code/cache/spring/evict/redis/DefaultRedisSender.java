package com.shit.code.cache.spring.evict.redis;

import com.shit.code.cache.spring.ServerInfo;
import com.shit.code.cache.spring.evict.EvictInfo;
import com.shit.code.cache.spring.evict.Sender;
import com.shit.code.redis.spring.message.RedisMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author Anthony
 * @date 11/9/20
 **/
@Slf4j
public class DefaultRedisSender implements Sender {

    public DefaultRedisSender(String channel, RedisTemplate<?, ?> redisTemplate) {
        super();
        this.CHANNEL = channel;
        this.redisTemplate = redisTemplate;
    }

    private final RedisTemplate<?, ?> redisTemplate;

    private final String CHANNEL;

    @Override
    public void send(EvictInfo evictInfo) {
        evictInfo.setFrom(ServerInfo.INSTANCE);
        log.info("Redis通知淘汰缓存：{}", evictInfo);
        redisTemplate.convertAndSend(CHANNEL, new RedisMessage<>(evictInfo));
    }
}
