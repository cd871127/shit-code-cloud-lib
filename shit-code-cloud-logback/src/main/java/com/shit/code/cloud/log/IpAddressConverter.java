package com.shit.code.cloud.log;


import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Anthony Chen
 * @date 2020/8/13
 **/
public class IpAddressConverter extends ClassicConverter {
    private static String IPS;

    static {
        try {
            IPS = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            IPS = "NULL";
            e.printStackTrace();
        }

    }

    @Override
    public String convert(ILoggingEvent event) {
        return IPS;
    }
}
