package com.shit.code.cloud.redis.config;

import com.shit.code.cloud.redis.RedisInitializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnBean(name = {"redisTemplate"})
public class RedisAutoConfiguration {

    @Bean
    public RedisInitializer redisInitializer() {
        return new RedisInitializer();
    }
}
