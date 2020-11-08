package com.shit.code.cache.spring.evict;

/**
 * 接受删除缓存通知
 *
 * @author Anthony
 * @date 11/8/20
 **/
public interface Receiver {

    /**
     * 接受通知
     */
    void receive(EvictInfo evictInfo);
}
