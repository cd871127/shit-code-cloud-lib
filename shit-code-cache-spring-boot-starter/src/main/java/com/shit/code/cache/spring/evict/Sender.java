package com.shit.code.cache.spring.evict;

/**
 * 发送清空缓存通知
 *
 * @author Anthony
 * @date 11/8/20
 **/
public interface Sender {

    /**
     * 发送淘汰信息
     *
     * @param evictInfo
     */
    void send(EvictInfo evictInfo);
}
