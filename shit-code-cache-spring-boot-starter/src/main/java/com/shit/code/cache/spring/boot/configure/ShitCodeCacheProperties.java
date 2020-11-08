package com.shit.code.cache.spring.boot.configure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Anthony
 * @date 11/9/20
 **/
@Data
@ConfigurationProperties(prefix = "shit.code.cache")
public class ShitCodeCacheProperties {

    private NoticeConfig notice = new NoticeConfig();

    @Data
    public static class NoticeConfig {
        private RedisConfig redis = new RedisConfig();

        @Data
        public static class RedisConfig {
            private String channel = "DEFAULT_REDIS_EVICT_CHANNEL";
        }
    }
}
