package com.shit.code.cloud.log;


import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.shit.code.cloud.common.NetUtils;

/**
 * @author Anthony Chen
 * @date 2020/8/13
 **/
public class IpAddressConverter extends ClassicConverter {
    private static final String IPS;

    static {
        IPS = NetUtils.getRealIpAddress().stream()
                .reduce((s, s2) -> s + ", " + s2).orElse("");
    }

    @Override
    public String convert(ILoggingEvent event) {
        return IPS;
    }
}
