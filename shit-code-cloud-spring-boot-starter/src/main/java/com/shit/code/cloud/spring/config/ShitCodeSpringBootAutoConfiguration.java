package com.shit.code.cloud.spring.config;

import com.shit.code.cloud.spring.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class ShitCodeSpringBootAutoConfiguration {

    @Bean
    public SpringContextUtil springContextUtil() {
        log.info("创建SpringContextUtil");
        return new SpringContextUtil();
    }
}
