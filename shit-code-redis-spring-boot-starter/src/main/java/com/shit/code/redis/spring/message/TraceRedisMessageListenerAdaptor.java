package com.shit.code.redis.spring.message;

import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * redis 消息监听的适配器
 *
 * @author Anthony
 * @date 11/7/20
 **/
@Slf4j
@EqualsAndHashCode(callSuper = true)
public class TraceRedisMessageListenerAdaptor extends RedisMessageListenerAdaptor {


    public TraceRedisMessageListenerAdaptor(TraceRedisMessageHandler<?> traceRedisMessageHandler, String topic) {
        super(traceRedisMessageHandler, topic);
    }

    public TraceRedisMessageListenerAdaptor(TraceRedisMessageHandler<?> traceRedisMessageHandler, List<String> topics) {
        super(traceRedisMessageHandler, topics);
    }

}
