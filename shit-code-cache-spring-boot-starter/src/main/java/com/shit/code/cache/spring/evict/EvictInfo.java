package com.shit.code.cache.spring.evict;

import com.shit.code.cache.spring.ServerInfo;
import lombok.Data;

/**
 * 清除缓存的信息
 *
 * @author Anthony
 * @date 11/8/20
 **/
@Data
public class EvictInfo {

    public EvictInfo() {
    }

    public EvictInfo(String cacheName) {
        this();
        this.cacheName = cacheName;
    }

    public EvictInfo(String cacheName, Object key) {
        this(cacheName);
        this.key = key;
    }

    private String cacheName;

    private Object key;

    /**
     * 从哪里来的信息，如果是自己就不用处理
     */
    private ServerInfo from;

}
