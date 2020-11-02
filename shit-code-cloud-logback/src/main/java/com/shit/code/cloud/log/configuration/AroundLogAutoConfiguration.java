package com.shit.code.cloud.log.configuration;

import com.shit.code.cloud.log.LogLevel;
import com.shit.code.cloud.log.aspect.AnnotationAroundLogAspect;
import com.shit.code.cloud.log.aspect.MethodAroundLogAspect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author Anthony
 * @date 11/3/20
 **/
@Slf4j
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AroundLogAutoConfiguration {

    @Bean
    AnnotationAroundLogAspect annotationAroundLogAspect() {
        log.debug("AnnotationAroundLogAspect created");
        return new AnnotationAroundLogAspect();
    }

    @Bean
    MethodAroundLogAspect methodAroundLogAspect() {
        log.debug("MethodAroundLogAspect created, logLevel:{}", LogLevel.DEBUG);
        return new MethodAroundLogAspect(LogLevel.DEBUG);
    }
}
