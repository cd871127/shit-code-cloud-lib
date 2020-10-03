package com.shit.code.cloud.cache;

import com.shit.code.cloud.cache.lock.CacheLock;
import com.shit.code.cloud.cache.lock.NoLock;
import com.shit.code.cloud.cache.store.DataStore;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.support.AbstractValueAdaptingCache;
import org.springframework.lang.NonNull;

import java.util.concurrent.Callable;

/**
 * @author Anthony
 * @date 10/1/20
 **/
@Slf4j
public class MultiLevelCache extends AbstractValueAdaptingCache {

    public static final String DEFAULT_NAME = "default";

    public final String name;

    private static final ThreadLocal<CacheConfig> CACHE_CONFIG_THREAD_LOCAL = ThreadLocal.withInitial(CacheConfig::defaultConfig);

    private final DataStore l1Cache;

    private final DataStore l2Cache;

    private CacheLock cacheLock;

    public MultiLevelCache(String name, boolean allowNullValues, DataStore l1Cache, DataStore l2Cache) {
        super(allowNullValues);
        this.name = name;
        this.l1Cache = l1Cache;
        this.l2Cache = l2Cache;
    }

    public MultiLevelCache(String name, boolean allowNullValues, DataStore l1Cache, DataStore l2Cache, CacheLock cacheLock) {
        this(name, allowNullValues, l1Cache, l2Cache);
        this.cacheLock = cacheLock;
    }

    @Override
    protected Object lookup(@NonNull Object key) {
        log.info("lookup");
        CacheConfig cacheConfig = CACHE_CONFIG_THREAD_LOCAL.get();
        Object value = l1Cache.get(key);
        if (value != null) {
            log.info("key:{},命中一级缓存", key);
            CACHE_CONFIG_THREAD_LOCAL.remove();
            return value;
        }

        value = l2Cache.get(key);
        if (value != null) {
            log.info("key:{},命中二级缓存", key);
            l1Cache.put(key, new DataStore.DataEntity(value, cacheConfig));
            CACHE_CONFIG_THREAD_LOCAL.remove();
            return value;
        }
        log.info("key:{},未命中缓存", key);
        return null;
    }

    @Override
    @NonNull
    public String getName() {
        return this.name;
    }

    @Override
    @NonNull
    public Object getNativeCache() {
        log.info("getNativeCache");
        return this;
    }

    /**
     * 同时只有一个线程读db
     *
     * @param key
     * @param valueLoader
     * @param <T>
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(@NonNull Object key, @NonNull Callable<T> valueLoader) {
        Object value = lookup(key);
        if (value != null) {
            return (T) value;
        }
        log.info("get2");
        if (cacheLock == null) {
            cacheLock = new NoLock();
        }
        CacheConfig cacheConfig = CACHE_CONFIG_THREAD_LOCAL.get();
        long start = System.currentTimeMillis();
        while (value == null && System.currentTimeMillis() - start <= cacheConfig.getSpanMillis()) {
            String lockKey = key.toString();
            String lockId = cacheLock.lock(key.toString(), cacheConfig.getLockMillis());
            try {
                if (StringUtils.isNotEmpty(lockId)) {
                    value = toStoreValue(valueLoader.call());
                    put(key, value);
                } else {
                    value = lookup(key);
                }
            } catch (Throwable ex) {
                throw new ValueRetrievalException(key, valueLoader, ex);
            } finally {
                cacheLock.unlock(lockKey, lockId);
            }
        }
        return (T) value;
    }

    @Override
    public void put(@NonNull Object key, Object value) {
        log.info("put");
        CacheConfig cacheConfig = CACHE_CONFIG_THREAD_LOCAL.get();
        CACHE_CONFIG_THREAD_LOCAL.remove();
        DataStore.DataEntity dataEntity = new DataStore.DataEntity(toStoreValue(value), cacheConfig);
        l2Cache.put(key, dataEntity);
        l1Cache.put(key, dataEntity);
    }

    @Override
    public void evict(@NonNull Object key) {
        log.info("evict");
        l2Cache.remove(key);
        l1Cache.remove(key);
    }

    @Override
    public void clear() {
        log.info("clear");
        l2Cache.clear();
        l1Cache.clear();
    }

    public void setCacheConfig(CacheConfig cacheConfig) {
        CACHE_CONFIG_THREAD_LOCAL.set(cacheConfig);
    }
}
