package com.shit.code.cache.spring;

import com.shit.code.common.io.net.NetUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;

import java.util.HashSet;

/**
 * 获取ip端口
 *
 * @author Anthony
 * @date 11/9/20
 **/
@Slf4j
public class WebServerInitializedListener implements ApplicationListener<WebServerInitializedEvent> {
    @Override
    public void onApplicationEvent(@NonNull WebServerInitializedEvent webServerInitializedEvent) {
        ServerInfo.INSTANCE.setIpList(new HashSet<>(NetUtils.getRealIpAddress()));
        ServerInfo.INSTANCE.setPort(webServerInitializedEvent.getWebServer().getPort());
    }
}
