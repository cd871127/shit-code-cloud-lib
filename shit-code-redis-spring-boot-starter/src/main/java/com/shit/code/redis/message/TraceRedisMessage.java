package com.shit.code.redis.message;

import brave.Tracer;

/**
 * @author Anthony
 * @date 11/7/20
 **/
public class TraceRedisMessage<T> extends RedisMessage<T> {
    Tracer tracer;
}
