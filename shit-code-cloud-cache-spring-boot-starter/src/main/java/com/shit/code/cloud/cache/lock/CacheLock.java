package com.shit.code.cloud.cache.lock;

/**
 * @author Anthony
 * @date 10/4/20
 **/
public interface CacheLock {

    /**
     * 加锁
     *
     * @param lockKey    key
     * @param lockMillis 锁的时间
     * @return
     */
    String lock(String lockKey, long lockMillis);

    /**
     * 解锁
     *
     * @param lockKey
     * @param lockId
     * @return
     */
    boolean unlock(String lockKey, String lockId);
}
