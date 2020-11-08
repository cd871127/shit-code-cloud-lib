package com.shit.code.redis.spring.message;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Map;

/**
 * @author Anthony
 * @date 11/7/20
 **/
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class TraceRedisMessage<T> extends RedisMessage<T> {

    private Map<String, String> traceContextMap;

}
