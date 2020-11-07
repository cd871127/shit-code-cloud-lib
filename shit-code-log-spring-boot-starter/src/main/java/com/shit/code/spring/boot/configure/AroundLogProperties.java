package com.shit.code.spring.boot.configure;

import com.shit.code.spring.log.LogLevel;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.shit.code.spring.boot.configure.AroundLogProperties.CONFIG_PREFIX;

/**
 * @author Anthony
 * @date 11/3/20
 **/
@Data
@ConfigurationProperties(prefix = CONFIG_PREFIX)
public class AroundLogProperties {

    public static final String CONFIG_PREFIX = "shit.code.log.around";

    private MethodLogConfig method = new MethodLogConfig();

    private AnnotationLogConfig annotation = new AnnotationLogConfig();

    private RequestMappingLogConfig requestMapping = new RequestMappingLogConfig();

    @Data
    public static class MethodLogConfig {
        private boolean enable = false;

        private LogLevel level = LogLevel.DEBUG;
    }

    @Data
    public static class AnnotationLogConfig {
        private boolean enable = true;
    }

    @Data
    public static class RequestMappingLogConfig {
        private boolean enable = true;

        private LogLevel level = LogLevel.INFO;
    }
}
