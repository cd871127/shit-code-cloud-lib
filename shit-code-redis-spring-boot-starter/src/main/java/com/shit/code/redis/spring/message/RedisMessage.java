package com.shit.code.redis.spring.message;

import brave.Span;
import brave.Tracing;
import brave.propagation.TraceContext;
import com.shit.code.redis.spring.trace.Setter;
import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * redis的消息体
 *
 * @author Anthony
 * @date 11/7/20
 **/
@ToString
public class RedisMessage<T> {

    @Getter
    private String messageId;

    @Getter
    @lombok.Setter
    private T body;

    @Getter
    private Map<String, String> traceContextMap;

    public RedisMessage() {
        init();
    }

    public RedisMessage(T body) {
        this();
        this.body = body;
    }

    private void init() {
        Span currentSpan = Tracing.currentTracer().currentSpan();
        if (currentSpan == null) {
            return;
        }
        TraceContext traceContext = currentSpan.context();
        Map<String, String> traceContextMap = new HashMap<>(4);
        TraceContext.Injector<Map<String, String>> injector = Tracing.current().propagation().injector(new Setter());
        injector.inject(traceContext, traceContextMap);
        this.traceContextMap = traceContextMap;
        this.messageId = generateMessageId();
    }

    private String generateMessageId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
