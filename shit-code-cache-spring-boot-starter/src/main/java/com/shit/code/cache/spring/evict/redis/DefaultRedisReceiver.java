package com.shit.code.cache.spring.evict.redis;

import com.shit.code.cache.spring.evict.EvictInfo;
import com.shit.code.cache.spring.evict.Receiver;
import com.shit.code.redis.spring.message.RedisMessageHandler;
import com.shit.code.redis.spring.message.RedisMessageListenerAdaptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author Anthony
 * @date 11/9/20
 **/
@Slf4j
public class DefaultRedisReceiver extends RedisMessageListenerAdaptor implements InitializingBean, RedisMessageHandler<EvictInfo>, Receiver {

    private final Receiver delegate;


    public DefaultRedisReceiver(String channel, Receiver receiver) {
        super(channel);
        this.delegate = receiver;
    }

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        setDelegate(this);
    }

    @Override
    public void receive(EvictInfo evictInfo) {
        delegate.receive(evictInfo);
    }


    @Override
    public void handle(EvictInfo evictInfo) {
        receive(evictInfo);
    }
}
