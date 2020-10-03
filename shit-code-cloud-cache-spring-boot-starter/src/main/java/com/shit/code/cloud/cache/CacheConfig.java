package com.shit.code.cloud.cache;

import lombok.Data;
import lombok.experimental.Accessors;

import java.lang.reflect.Type;

/**
 * @author Anthony
 * @date 10/1/20
 **/
@Data
@Accessors(chain = true)
public class CacheConfig {

    /**
     * 缓存时间
     */
    private long ttl;

    private Type type;

    /**
     * 序列化读操作的锁时长
     */
    private long lockMillis;

    /**
     * 多少毫秒没有获取锁就读db,自旋时长
     */
    private long spanMillis;

    public static CacheConfig defaultConfig() {
        CacheConfig cacheConfig = new CacheConfig();
        cacheConfig.setTtl(3000L);
        return cacheConfig;
    }
}
