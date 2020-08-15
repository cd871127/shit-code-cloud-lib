package com.shit.code.cloud.mybatis.config;

import com.shit.code.cloud.mybatis.log.SqlLogInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "assemble", prefix = "shit.code.mybatis.sql", havingValue = "true")
@Slf4j
public class MybatisAutoConfiguration {
    @Bean
    public SqlLogInterceptor sqlLogInterceptor() {
        log.warn("打印完整SQL日志，比较耗时，不建议在生产使用");
        return new SqlLogInterceptor();
    }
}
