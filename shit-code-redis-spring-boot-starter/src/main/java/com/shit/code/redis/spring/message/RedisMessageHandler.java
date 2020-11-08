package com.shit.code.redis.spring.message;

import brave.Span;
import brave.Tracer;
import brave.Tracing;
import brave.propagation.TraceContext;
import brave.propagation.TraceContextOrSamplingFlags;
import com.shit.code.common.io.net.NetUtils;
import com.shit.code.redis.spring.trace.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.util.List;
import java.util.Map;

import static brave.Span.Kind.CONSUMER;

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
        TraceContext.Extractor<Map<String, String>> extractor = Tracing.current().propagation().extractor(new Getter());
        Span span;
        Tracer tracer = Tracing.currentTracer();
        Map<String, String> traceContextMap = redisMessage.getTraceContextMap();
        if (traceContextMap == null || traceContextMap.isEmpty()) {
            logger.debug("订阅没有span信息");
            span = tracer.nextSpan();
        } else {
            TraceContextOrSamplingFlags extracted = extractor.extract(traceContextMap);
            logger.debug("订阅信息: {}", extracted);
            span = tracer.nextSpan(extracted);
        }

        if (!span.isNoop()) {
            span.kind(CONSUMER).start();
            Class<?> clazz = getClass();
            String service = clazz.getName();
            String method = null;
            try {
                method = clazz.getMethod(MessageListenerAdapter.ORIGINAL_DEFAULT_LISTENER_METHOD, RedisMessage.class).getName();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            span.kind(CONSUMER);
            span.name(service + "#" + method);
            //TODO anthony 这里需要获取服务器的ip和端口
            List<String> ips = NetUtils.getRealIpAddress();
            if (!ips.isEmpty()) {
                span.remoteIpAndPort(ips.get(0), 8080);
            }
        }
        try (Tracer.SpanInScope scope = tracer.withSpanInScope(span)) {
            logger.info("收到Redis订阅: {}", redisMessage.getMessageId());
            handle(redisMessage.getBody());
            logger.info("完成Redis订阅: {}", redisMessage.getMessageId());
        } finally {
            span.finish();
        }
    }

    /**
     * 业务逻辑
     *
     * @param t
     */
    void handle(T t);

}
