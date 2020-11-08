package com.shit.code.redis.spring.trace;

import brave.propagation.Propagation;

import java.util.Map;

/**
 * @author Anthony
 * @date 11/8/20
 **/
public class Setter implements Propagation.Setter<Map<String, String>, String> {
    @Override
    public void put(Map<String, String> carrier, String key, String value) {
        carrier.put(key, value);
    }

    @Override
    public String toString() {
        return "Map::set";
    }
}
