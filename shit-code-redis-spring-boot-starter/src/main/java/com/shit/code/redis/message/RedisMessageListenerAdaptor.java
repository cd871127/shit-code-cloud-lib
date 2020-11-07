package com.shit.code.redis.message;

import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.util.Collections;
import java.util.List;

import static com.shit.code.redis.serializer.DefaultJsonSerializerFactory.getSerializer;

/**
 * redis 消息监听的适配器
 *
 * @author Anthony
 * @date 11/7/20
 **/
@Slf4j
@EqualsAndHashCode(callSuper = true)
public class RedisMessageListenerAdaptor extends MessageListenerAdapter {

    private final List<String> topics;

    public RedisMessageListenerAdaptor(RedisMessageHandler<?> redisMessageHandler, String topic) {
        this(redisMessageHandler, Collections.singletonList(topic));
    }

    public RedisMessageListenerAdaptor(RedisMessageHandler<?> redisMessageHandler, List<String> topics) {
        super(redisMessageHandler, MessageListenerAdapter.ORIGINAL_DEFAULT_LISTENER_METHOD);
        this.topics = topics;
        setSerializer(getSerializer());
    }

    public List<String> getTopics() {
        assert topics != null && !topics.isEmpty();
        return topics;
    }

}
