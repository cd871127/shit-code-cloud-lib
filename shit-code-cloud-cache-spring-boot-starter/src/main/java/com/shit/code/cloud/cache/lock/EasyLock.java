package com.shit.code.cloud.cache.lock;

/**
 * @author Anthony
 * @date 10/4/20
 **/
public class EasyLock implements CacheLock {

    volatile int i = 0;

    @Override
    public synchronized String lock(String lockKey, long lockMillis) {
        if (i == 0) {
            i = 1;
            return String.valueOf(Thread.currentThread().getId());
        } else {
            return null;
        }
    }

    @Override
    public synchronized boolean unlock(String lockKey, String lockId) {
        if (String.valueOf(Thread.currentThread().getId()).equals(lockId)) {
            i = 0;
            return true;
        }
        return false;
    }
}
