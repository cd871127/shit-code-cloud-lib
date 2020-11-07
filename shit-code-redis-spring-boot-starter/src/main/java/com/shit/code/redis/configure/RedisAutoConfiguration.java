package com.shit.code.redis.configure;

import com.shit.code.redis.RedisSerializerInitializer;
import com.shit.code.redis.message.RedisMessageListenerAdaptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author anthony
 */
@Slf4j
@Configuration
public class RedisAutoConfiguration {

    @Bean
    public RedisSerializerInitializer redisInitializer() {
        return new RedisSerializerInitializer();
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory, List<RedisMessageListenerAdaptor> redisMessageListenerAdaptors) {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
        if (redisMessageListenerAdaptors == null || redisMessageListenerAdaptors.isEmpty()) {
            log.warn("没有找到messageListenerAdapters");
        } else {
            redisMessageListenerAdaptors
                    .forEach(redisMessageListenerAdaptor -> {
                        List<Topic> topics = redisMessageListenerAdaptor.getTopics().stream()
                                .map(PatternTopic::new).collect(Collectors.toList());
                        redisMessageListenerContainer.addMessageListener(redisMessageListenerAdaptor, topics);
                    });
        }
        return redisMessageListenerContainer;
    }

//    @Bean
////    @ConditionalOnBean({RedisConnectionFactory.class, MessageListenerAdapter.class})
//    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory, ApplicationContext applicationContext) {
//        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
//        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
//
//        String[] listenerBeanNames = applicationContext.getBeanNamesForAnnotation(RedisMessageListener.class);
//        if (listenerBeanNames.length == 0) {
//            log.warn("没有找到messageListenerAdapters");
//        } else {
//            Map<MessageListenerAdapter, List<ChannelTopic>> messageListenerAdapterTopicMap = new HashMap<>(listenerBeanNames.length);
//            Arrays.stream(listenerBeanNames).forEach(
//                    beanName -> {
//                        MessageListenerAdapter messageListenerAdapter = applicationContext.getBean(beanName, MessageListenerAdapter.class);
//                        RedisMessageListener redisMessageListener = messageListenerAdapter.getClass().getAnnotation(RedisMessageListener.class);
//                        List<ChannelTopic> topics = Arrays.stream(redisMessageListener.channels())
//                                .map(ChannelTopic::new).collect(Collectors.toList());
//                        messageListenerAdapterTopicMap.put(messageListenerAdapter, topics);
//                    }
//            );
//            redisMessageListenerContainer.setMessageListeners(Collections.unmodifiableMap(messageListenerAdapterTopicMap));
//        }
//
//        return redisMessageListenerContainer;
//    }
}
