package com.shit.code.cloud.cache;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Anthony
 * @date 10/1/20
 **/
@Configuration
public class MultiLevelCacheConfiguration {

    @Bean(name = "multiLevelCacheManager")
    MultiLevelCacheManager multiLevelCacheManager() {
        return new MultiLevelCacheManager();
    }

    @Bean(name = "multiLevelCacheResolver")
    MultiLevelCacheResolver multiLevelCacheResolver(MultiLevelCacheManager multiLevelCacheManager) {
        return new MultiLevelCacheResolver(multiLevelCacheManager);
    }

}
