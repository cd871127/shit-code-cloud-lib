package shit.code.cloud.spring.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author anthonychen
 * @date 2020/10/14
 **/
@Slf4j
@RestControllerAdvice
public class CommonExceptionAdvice {

    @ExceptionHandler(Exception.class)
    public String test(Exception e) {
        return "";
    }
}
