package com.shit.code.web.spring.advice;

import com.shit.code.common.exception.BusinessException;
import com.shit.code.common.exception.BusinessExceptionEnum;
import com.shit.code.common.exception.CriticalException;
import com.shit.code.common.web.response.ExceptionHttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 统一异常处理
 *
 * @author anthonychen
 * @date 2020/10/14
 **/
@Slf4j
@RestControllerAdvice(basePackages = "com.shit.code.cloud")
public class ShitCodeExceptionAdvice {

    @ExceptionHandler(BusinessException.class)
    public ExceptionHttpResponse businessException(BusinessException businessException) {
        if (businessException instanceof CriticalException) {
            log.error("致命业务异常：{}", businessException.getMsg());
        } else {
            log.error("普通业务异常：{}", businessException.getMsg());
        }
        ExceptionHttpResponse exceptionHttpResponse = new ExceptionHttpResponse(businessException);
        //TODO 异常上报
        return handleResponse(exceptionHttpResponse, businessException);
    }

//    @ExceptionHandler(NormalException.class)
//    public ExceptionHttpResponse normalException(NormalException normalException) {
//        log.error("普通业务异常：{}", normalException.getMsg());
//        ExceptionHttpResponse exceptionHttpResponse = new ExceptionHttpResponse(normalException);
//        return handleResponse(exceptionHttpResponse, normalException);
//    }

    @ExceptionHandler(Exception.class)
    public ExceptionHttpResponse exception(Exception exception) {
        log.error("系统异常\n{}", ExceptionUtils.getStackTrace(exception));
        ExceptionHttpResponse exceptionHttpResponse = new ExceptionHttpResponse(BusinessExceptionEnum.SYSTEM_EXCEPTION.getCode(), exception.getMessage());

        //TODO 异常上报
//        throw exception;
        return handleResponse(exceptionHttpResponse, exception);
    }

    private ExceptionHttpResponse handleResponse(ExceptionHttpResponse exceptionHttpResponse, Exception exception) {
        exceptionHttpResponse.setExceptionClassName(exception.getClass().getName());
        exceptionHttpResponse.setTraceId(getTraceId());
        return exceptionHttpResponse;
    }

    private String getTraceId() {
        String traceId = null;
        try {
            traceId = MDC.get("traceId");
        } catch (Exception e) {
            log.warn("设置TraceId异常：{}", ExceptionUtils.getStackTrace(e));
        }
        return traceId;
    }
}
