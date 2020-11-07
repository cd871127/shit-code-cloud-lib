package com.shit.code.cache.spring.web.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.shit.code.cache.spring.web.advice")
@Slf4j
public class ShitCodeSpringBootAdviceAutoConfiguration {
}
