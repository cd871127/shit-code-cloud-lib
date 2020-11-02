package com.shit.code.cloud.log.configuration;

import com.shit.code.cloud.log.LogLevel;
import com.shit.code.cloud.log.aspect.AnnotationAroundLogAspect;
import com.shit.code.cloud.log.aspect.MethodAroundLogAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Anthony
 * @date 11/3/20
 **/
@Configuration
public class AroundLogAutoConfiguration {

    @Bean
    AnnotationAroundLogAspect annotationAroundLogAspect() {
        return new AnnotationAroundLogAspect();
    }

    @Bean
    MethodAroundLogAspect methodAroundLogAspect() {
        return new MethodAroundLogAspect(LogLevel.DEBUG);
    }
}
