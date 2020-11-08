package com.shit.code.cache.spring.boot.configure;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.CaffeineSpec;
import com.shit.code.cache.spring.ShitCodeCacheManager;
import com.shit.code.cache.spring.WebServerInitializedListener;
import com.shit.code.cache.spring.evict.Sender;
import com.shit.code.cache.spring.evict.redis.DefaultReceiver;
import com.shit.code.cache.spring.evict.redis.DefaultRedisReceiver;
import com.shit.code.cache.spring.evict.redis.DefaultRedisSender;
import com.shit.code.redis.spring.boot.configure.RedisAutoConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizers;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.LinkedHashSet;
import java.util.List;

/**
 * @author Anthony
 * @date 11/7/20
 **/
@Slf4j
@Configuration
@EnableConfigurationProperties({ShitCodeCacheProperties.class, CacheProperties.class})
@AutoConfigureAfter({CacheAutoConfiguration.class, RedisAutoConfiguration.class})
public class ShitCodeCacheAutoConfiguration {

    @Bean
    @Primary
    ShitCodeCacheManager shitCodeCacheManager(List<Sender> senders, RedisCacheManager redisCacheManager, CaffeineCacheManager caffeineCacheManager) {
        log.debug("初始化ShitCodeCacheManager");
        return new ShitCodeCacheManager().setRedisCacheManager(redisCacheManager).setCaffeineCacheManager(caffeineCacheManager).setSenders(senders);
    }

    @Bean
    RedisCacheManager redisCacheManager(CacheProperties cacheProperties, CacheManagerCustomizers cacheManagerCustomizers,
                                        ObjectProvider<RedisCacheConfiguration> redisCacheConfiguration,
                                        ObjectProvider<RedisCacheManagerBuilderCustomizer> redisCacheManagerBuilderCustomizers,
                                        RedisConnectionFactory redisConnectionFactory, ResourceLoader resourceLoader) {
        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(
                determineConfiguration(cacheProperties, redisCacheConfiguration, resourceLoader.getClassLoader()));
        List<String> cacheNames = cacheProperties.getCacheNames();
        if (!cacheNames.isEmpty()) {
            builder.initialCacheNames(new LinkedHashSet<>(cacheNames));
        }
        redisCacheManagerBuilderCustomizers.orderedStream().forEach((customizer) -> customizer.customize(builder));
        return cacheManagerCustomizers.customize(builder.build());
    }

    private org.springframework.data.redis.cache.RedisCacheConfiguration determineConfiguration(
            CacheProperties cacheProperties,
            ObjectProvider<org.springframework.data.redis.cache.RedisCacheConfiguration> redisCacheConfiguration,
            ClassLoader classLoader) {
        return redisCacheConfiguration.getIfAvailable(() -> createConfiguration(cacheProperties, classLoader));
    }

    private org.springframework.data.redis.cache.RedisCacheConfiguration createConfiguration(
            CacheProperties cacheProperties, ClassLoader classLoader) {
        CacheProperties.Redis redisProperties = cacheProperties.getRedis();
        org.springframework.data.redis.cache.RedisCacheConfiguration config = org.springframework.data.redis.cache.RedisCacheConfiguration
                .defaultCacheConfig();
        config = config.serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(new JdkSerializationRedisSerializer(classLoader)));
        if (redisProperties.getTimeToLive() != null) {
            config = config.entryTtl(redisProperties.getTimeToLive());
        }
        if (redisProperties.getKeyPrefix() != null) {
            config = config.prefixCacheNameWith(redisProperties.getKeyPrefix());
        }
        if (!redisProperties.isCacheNullValues()) {
            config = config.disableCachingNullValues();
        }
        if (!redisProperties.isUseKeyPrefix()) {
            config = config.disableKeyPrefix();
        }
        return config;
    }

    @Bean
    CaffeineCacheManager caffeineCacheManager(CacheProperties cacheProperties, CacheManagerCustomizers customizers,
                                              ObjectProvider<Caffeine<Object, Object>> caffeine, ObjectProvider<CaffeineSpec> caffeineSpec,
                                              ObjectProvider<CacheLoader<Object, Object>> cacheLoader) {
        CaffeineCacheManager cacheManager = createCacheManager(cacheProperties, caffeine, caffeineSpec, cacheLoader);
        List<String> cacheNames = cacheProperties.getCacheNames();
        if (!CollectionUtils.isEmpty(cacheNames)) {
            cacheManager.setCacheNames(cacheNames);
        }
        return customizers.customize(cacheManager);
    }

    private CaffeineCacheManager createCacheManager(CacheProperties cacheProperties,
                                                    ObjectProvider<Caffeine<Object, Object>> caffeine, ObjectProvider<CaffeineSpec> caffeineSpec,
                                                    ObjectProvider<CacheLoader<Object, Object>> cacheLoader) {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        setCacheBuilder(cacheProperties, caffeineSpec.getIfAvailable(), caffeine.getIfAvailable(), cacheManager);
        cacheLoader.ifAvailable(cacheManager::setCacheLoader);
        return cacheManager;
    }

    private void setCacheBuilder(CacheProperties cacheProperties, CaffeineSpec caffeineSpec,
                                 Caffeine<Object, Object> caffeine, CaffeineCacheManager cacheManager) {
        String specification = cacheProperties.getCaffeine().getSpec();
        if (StringUtils.hasText(specification)) {
            cacheManager.setCacheSpecification(specification);
        } else if (caffeineSpec != null) {
            cacheManager.setCaffeineSpec(caffeineSpec);
        } else if (caffeine != null) {
            cacheManager.setCaffeine(caffeine);
        }
    }

    @Bean
    public WebServerInitializedListener webServerInitializedListener() {
        return new WebServerInitializedListener();
    }

    @Bean
    public DefaultRedisSender defaultRedisSender(ShitCodeCacheProperties shitCodeCacheProperties, RedisTemplate<?, ?> redisTemplate) {
        return new DefaultRedisSender(shitCodeCacheProperties.getNotice().getRedis().getChannel(), redisTemplate);
    }

    @Bean
    public DefaultReceiver defaultReceiver(ShitCodeCacheManager shitCodeCacheManager) {
        return new DefaultReceiver(shitCodeCacheManager);
    }

    @Bean
    public DefaultRedisReceiver defaultRedisReceiver(ShitCodeCacheProperties shitCodeCacheProperties, DefaultReceiver defaultReceiver) {
        return new DefaultRedisReceiver(shitCodeCacheProperties.getNotice().getRedis().getChannel(), defaultReceiver);
    }

}
