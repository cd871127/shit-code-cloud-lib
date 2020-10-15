package shit.code.cloud.spring.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("shit.code.cloud.spring.exception")
@Slf4j
public class ShitCodeSpringBootExceptionAutoConfiguration {
}
