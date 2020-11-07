package com.shit.code.cache.spring.boot.configure;

import com.shit.code.cache.spring.utils.SpringContextUtil;
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
