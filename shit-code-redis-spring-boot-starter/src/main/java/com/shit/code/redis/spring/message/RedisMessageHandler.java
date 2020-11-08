package com.shit.code.redis.spring.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 消息处理器
 * 具体业务逻辑实现这个类
 *
 * @author Anthony
 * @date 11/7/20
 **/
public interface RedisMessageHandler<T> {

    Logger logger = LoggerFactory.getLogger(RedisMessageHandler.class);

    /**
     * 处理消息的方法
     */
    default void handleMessage(RedisMessage<T> redisMessage) {
        logger.info("收到Redis订阅: {}", redisMessage.getMessageId());
        handle(redisMessage.getBody());
        logger.info("完成Redis订阅: {}", redisMessage.getMessageId());
    }

    /**
     * 处理订阅的业务逻辑
     *
     * @param t
     */
    void handle(T t);
}
