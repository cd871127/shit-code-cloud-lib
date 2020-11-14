package com.shit.code.web.spring.boot.configure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.shit.code.web.spring.advice")
@Slf4j
public class ShitCodeSpringBootAdviceAutoConfiguration {
//    /**
//     * fastjson
//     *
//     * @return
//     */
//    @Bean
//    public HttpMessageConverters custHttpMessageConverter() {
//        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
//        fastJsonHttpMessageConverter.setCharset(StandardCharsets.UTF_8);
//        fastJsonHttpMessageConverter.setDefaultCharset(StandardCharsets.UTF_8);
//        fastJsonHttpMessageConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
//
//        return new HttpMessageConverters(fastJsonHttpMessageConverter);
//    }
}
