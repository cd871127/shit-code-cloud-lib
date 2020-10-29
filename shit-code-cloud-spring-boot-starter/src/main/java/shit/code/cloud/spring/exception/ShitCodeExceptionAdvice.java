package shit.code.cloud.spring.exception;

import com.shit.code.cloud.common.exception.ShitCodeException;
import com.shit.code.cloud.common.exception.ShitCodeExceptionEnum;
import com.shit.code.cloud.common.web.response.CommonHttpResponse;
import com.shit.code.cloud.common.web.response.HttpResponse;
import com.shit.code.cloud.common.web.response.StringHttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


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
        StringHttpResponse stringHttpResponse = new StringHttpResponse(ShitCodeExceptionEnum.FAILED);
        stringHttpResponse.setData(exceptionString);
        return stringHttpResponse;
    }
}
