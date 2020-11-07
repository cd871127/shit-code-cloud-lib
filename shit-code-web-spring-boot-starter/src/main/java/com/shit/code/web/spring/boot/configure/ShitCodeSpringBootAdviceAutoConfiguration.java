package com.shit.code.web.spring.boot.configure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.shit.code.web.spring.advice")
@Slf4j
public class ShitCodeSpringBootAdviceAutoConfiguration {
}
