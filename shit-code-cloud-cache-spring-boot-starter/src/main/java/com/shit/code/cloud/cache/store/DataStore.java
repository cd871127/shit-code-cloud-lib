package com.shit.code.cloud.cache.store;

import com.shit.code.cloud.cache.CacheConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Anthony
 * @date 10/1/20
 **/
public interface DataStore {

    Object get(Object key);

    void put(Object key, DataEntity dataEntity);

    void remove(Object key);

    void clear();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class DataEntity {
        private Object value;

        private CacheConfig cacheConfig;
    }
}
