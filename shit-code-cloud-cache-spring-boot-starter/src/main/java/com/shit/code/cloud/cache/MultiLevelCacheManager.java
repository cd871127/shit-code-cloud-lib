package com.shit.code.cloud.cache;

import com.shit.code.cloud.cache.lock.CacheLock;
import com.shit.code.cloud.cache.store.DataStore;
import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractCacheManager;
import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.Collections;

import static com.shit.code.cloud.cache.MultiLevelCache.DEFAULT_NAME;

/**
 * @author Anthony
 * @date 10/1/20
 **/
public class MultiLevelCacheManager extends AbstractCacheManager {

    private DataStore l1Cache;

    private DataStore l2Cache;

    private CacheLock cacheLock;

    public MultiLevelCacheManager(DataStore l1Cache, DataStore l2Cache, CacheLock cacheLock) {
        this.l1Cache = l1Cache;
        this.l2Cache = l2Cache;
        this.cacheLock = cacheLock;
    }

    @Override
    @NonNull
    protected Collection<? extends Cache> loadCaches() {
        return Collections.singletonList(new MultiLevelCache(DEFAULT_NAME, true, l1Cache, l2Cache, cacheLock));
    }

    @Override
    protected Cache getMissingCache(@NonNull String name) {
        return new MultiLevelCache(name, true, l1Cache, l2Cache, cacheLock);
    }

}
