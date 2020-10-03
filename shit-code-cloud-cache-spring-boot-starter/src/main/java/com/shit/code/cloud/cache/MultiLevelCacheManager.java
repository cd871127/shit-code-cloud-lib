package com.shit.code.cloud.cache;

import com.shit.code.cloud.cache.store.CaffeineDataStore;
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

    @Override
    @NonNull
    protected Collection<? extends Cache> loadCaches() {
        return Collections.singletonList(new MultiLevelCache(DEFAULT_NAME, true, new CaffeineDataStore(), new CaffeineDataStore()));
    }

    @Override
    protected Cache getMissingCache(@NonNull String name) {
        return new MultiLevelCache(name, true, new CaffeineDataStore(), new CaffeineDataStore());
    }

}
