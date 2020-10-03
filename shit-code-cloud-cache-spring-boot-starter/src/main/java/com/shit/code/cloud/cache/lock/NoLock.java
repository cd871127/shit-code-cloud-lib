package com.shit.code.cloud.cache.lock;

/**
 * @author Anthony
 * @date 10/4/20
 **/
public class NoLock implements CacheLock {


    @Override
    public synchronized String lock(String lockKey, long lockMillis) {
        return "ok";
    }

    @Override
    public synchronized boolean unlock(String lockKey, String lockId) {
        return true;
    }
}
