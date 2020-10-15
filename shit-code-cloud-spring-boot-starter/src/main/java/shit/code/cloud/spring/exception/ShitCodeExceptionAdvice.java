package shit.code.cloud.spring.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shit.code.cloud.spring.response.CommonHttpResponse;
import shit.code.cloud.spring.response.HttpResponse;
import shit.code.cloud.spring.response.StringHttpResponse;

import static shit.code.cloud.spring.exception.ShitCodeExceptionEnum.FAILED;

/**
 * @author anthonychen
 * @date 2020/10/14
 **/
@Slf4j
@RestControllerAdvice
public class ShitCodeExceptionAdvice {

    @ExceptionHandler(ShitCodeException.class)
    public HttpResponse shitCodeException(ShitCodeException shitCodeException) {
        ShitCodeExceptionEnum shitCodeExceptionEnum = shitCodeException.getShitCodeExceptionEnum();
        log.error("业务异常:{},{}", shitCodeExceptionEnum.getCode(), shitCodeExceptionEnum.getMsg());
        return new CommonHttpResponse(shitCodeException.getShitCodeExceptionEnum());
    }

    @ExceptionHandler(Exception.class)
    public HttpResponse exception(Exception exception) {
        String exceptionString = ExceptionUtils.getStackTrace(exception);
        log.error("系统异常:{}", exceptionString);
        StringHttpResponse stringHttpResponse = new StringHttpResponse(FAILED);
        stringHttpResponse.setData(exceptionString);
        return stringHttpResponse;
    }
}
