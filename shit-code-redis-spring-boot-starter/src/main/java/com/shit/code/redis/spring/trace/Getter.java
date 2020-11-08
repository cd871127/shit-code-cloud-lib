package com.shit.code.redis.spring.trace;

import brave.propagation.Propagation;

import java.util.Map;

/**
 * @author Anthony
 * @date 11/8/20
 **/
public class Getter implements Propagation.Getter<Map<String, String>, String> {
    @Override
    public String get(Map<String, String> carrier, String key) {
        return carrier.get(key);
    }

    @Override
    public String toString() {
        return "Map::get";
    }
}