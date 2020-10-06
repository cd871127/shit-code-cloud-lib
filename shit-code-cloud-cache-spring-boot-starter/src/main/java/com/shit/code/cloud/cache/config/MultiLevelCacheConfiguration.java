package com.shit.code.cloud.cache.config;

import com.shit.code.cloud.cache.MultiLevelCacheManager;
import com.shit.code.cloud.cache.MultiLevelCacheResolver;
import com.shit.code.cloud.cache.lock.CacheLock;
import com.shit.code.cloud.cache.lock.NoLock;
import com.shit.code.cloud.cache.store.CaffeineDataStore;
import com.shit.code.cloud.cache.store.DataStore;
import org.springframework.context.annotation.Bean;

/**
 * @author Anthony
 * @date 10/6/20
 **/
public class MultiLevelCacheConfiguration {

    @Bean("l1Cache")
    DataStore l1Cache() {
        return new CaffeineDataStore();
    }

    @Bean("l2Cache")
    DataStore l2Cache() {
        return new CaffeineDataStore();
    }

    @Bean
    CacheLock cacheLock() {
        return new NoLock();
    }

    @Bean(name = "multiLevelCacheManager")
    MultiLevelCacheManager multiLevelCacheManager(DataStore l1Cache, DataStore l2Cache, CacheLock cacheLock) {
        return new MultiLevelCacheManager(l1Cache, l2Cache, cacheLock);
    }

    @Bean(name = "multiLevelCacheResolver")
    MultiLevelCacheResolver multiLevelCacheResolver(MultiLevelCacheManager multiLevelCacheManager) {
        return new MultiLevelCacheResolver(multiLevelCacheManager);
    }


}
