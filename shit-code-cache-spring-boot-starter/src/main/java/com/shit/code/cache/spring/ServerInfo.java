package com.shit.code.cache.spring;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

/**
 * @author Anthony
 * @date 11/9/20
 **/
@EqualsAndHashCode
@Data
public class ServerInfo {

    public static final ServerInfo INSTANCE = new ServerInfo();

    private Set<String> ipList;

    private Integer port;


}
