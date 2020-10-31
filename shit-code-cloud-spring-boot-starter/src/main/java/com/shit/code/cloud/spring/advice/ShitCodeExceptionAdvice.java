package com.shit.code.cloud.spring.advice;

import com.shit.code.cloud.common.exception.CriticalException;
import com.shit.code.cloud.common.exception.NormalException;
import com.shit.code.cloud.common.exception.ShitCodeExceptionEnum;
import com.shit.code.cloud.common.web.response.ExceptionHttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 统一异常处理
 *
 * @author anthonychen
 * @date 2020/10/14
 **/
@Slf4j
@RestControllerAdvice
public class ShitCodeExceptionAdvice {

    @ExceptionHandler(CriticalException.class)
    public ExceptionHttpResponse criticalException(CriticalException criticalException) {
        log.error("致命业务异常：{}", criticalException.getMsg());
        ExceptionHttpResponse exceptionHttpResponse = new ExceptionHttpResponse(criticalException);
        exceptionHttpResponse.setExceptionClassName(criticalException.getClass().getName());
        //TODO 异常上报
        return exceptionHttpResponse;
    }

    @ExceptionHandler(NormalException.class)
    public ExceptionHttpResponse normalException(NormalException normalException) {
        log.error("普通业务异常：{}", normalException.getMsg());
        ExceptionHttpResponse exceptionHttpResponse = new ExceptionHttpResponse(normalException);
        exceptionHttpResponse.setExceptionClassName(normalException.getClass().getName());
        return exceptionHttpResponse;
    }

    @ExceptionHandler(Exception.class)
    public ExceptionHttpResponse exception(Exception exception) {
        log.error("系统异常\n{}", ExceptionUtils.getStackTrace(exception));
        ExceptionHttpResponse exceptionHttpResponse = new ExceptionHttpResponse(ShitCodeExceptionEnum.FAILED.getCode(), exception.getMessage());
        exceptionHttpResponse.setExceptionClassName(exception.getClass().getName());
        //TODO 异常上报
        return exceptionHttpResponse;
    }
}
