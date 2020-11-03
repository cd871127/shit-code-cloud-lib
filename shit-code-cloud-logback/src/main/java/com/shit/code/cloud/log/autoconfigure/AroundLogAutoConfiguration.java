package com.shit.code.cloud.log.autoconfigure;

import com.shit.code.cloud.log.aspect.AroundLogAnnotationAspect;
import com.shit.code.cloud.log.aspect.MethodAroundLogAspect;
import com.shit.code.cloud.log.aspect.RequestMappingAnnotationLogAspect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import static com.shit.code.cloud.log.autoconfigure.AroundLogProperties.CONFIG_PREFIX;

/**
 * @author Anthony
 * @date 11/3/20
 **/
@Slf4j
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableConfigurationProperties(AroundLogProperties.class)
public class AroundLogAutoConfiguration {


    @Bean
    @ConditionalOnProperty(name = "enable", prefix = CONFIG_PREFIX + ".annotation", havingValue = "true", matchIfMissing = true)
    AroundLogAnnotationAspect annotationAroundLogAspect() {
        log.debug("AnnotationAroundLogAspect created");
        return new AroundLogAnnotationAspect();
    }

    @Bean
    @ConditionalOnProperty(name = "enable", prefix = CONFIG_PREFIX + ".method", havingValue = "true")
    MethodAroundLogAspect methodAroundLogAspect(AroundLogProperties aroundLogProperties) {
        log.warn("创建方法AroundLog切面, 所有public方法都会拦截, 比较消耗性能, 不建议在生产环境使用");
        return new MethodAroundLogAspect(aroundLogProperties);
    }

    @Bean
    @ConditionalOnProperty(name = "enable", prefix = CONFIG_PREFIX + ".request-mapping", havingValue = "true")
    RequestMappingAnnotationLogAspect requestMappingAnnotationLogAspect(AroundLogProperties aroundLogProperties) {
        log.debug("RequestMappingAnnotationLogAspect created");
        return new RequestMappingAnnotationLogAspect(aroundLogProperties);
    }
}
