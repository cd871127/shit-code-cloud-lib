package shit.code.cloud.spring.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import shit.code.cloud.spring.utils.SpringContextUtil;

@Configuration
@Slf4j
public class SpringBootAutoConfiguration {

    @Bean
    public SpringContextUtil springContextUtil() {
        log.info("创建SpringContextUtil");
        return new SpringContextUtil();
    }
}
