package com.shit.code.redis.spring.message;

import lombok.Data;

import java.util.UUID;

/**
 * redis的消息体
 *
 * @author Anthony
 * @date 11/7/20
 **/
@Data
public class RedisMessage<T> {

    private String messageId;

    private T body;

    public RedisMessage() {
    }

    public RedisMessage(String messageId) {
        this(messageId, null);
    }

    public RedisMessage(T body) {
        this(UUID.randomUUID().toString().replaceAll("-", ""), body);
    }

    public RedisMessage(String messageId, T body) {
        this();
        this.messageId = messageId;
        this.body = body;
    }
}
