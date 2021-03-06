package com.shit.code.redis.spring.message;

/**
 * 消息处理器
 * 具体业务逻辑实现这个类
 *
 * @author Anthony
 * @date 11/7/20
 **/
public interface TraceRedisMessageHandler<T> {

    /**
     * 处理消息的方法
     */
    void handleMessage(TraceRedisMessage<T> traceRedisMessage);

}
