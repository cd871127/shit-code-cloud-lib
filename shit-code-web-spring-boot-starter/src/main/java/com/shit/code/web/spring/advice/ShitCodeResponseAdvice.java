package com.shit.code.web.spring.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shit.code.common.web.response.CommonHttpResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author Anthony
 * @date 11/1/20
 **/
@Slf4j
@RestControllerAdvice(basePackages = "com.shit.code.cloud")
public class ShitCodeResponseAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(@NonNull MethodParameter methodParameter, @NonNull Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object o, @NonNull MethodParameter methodParameter, @NonNull MediaType mediaType, @NonNull Class<? extends HttpMessageConverter<?>> aClass, @NonNull ServerHttpRequest serverHttpRequest, @NonNull ServerHttpResponse serverHttpResponse) {
//        if (o instanceof String) {
//            throw new IllegalStateException("Controller暂时不支持返回String类型");
//        }
        CommonHttpResponse commonHttpResponse = new CommonHttpResponse(o);
        String traceId = MDC.get("traceId");
        commonHttpResponse.setTraceId(traceId);
        if (methodParameter.getParameterType().equals(String.class)) {
            return new ObjectMapper().writeValueAsString(commonHttpResponse);

        }
        return commonHttpResponse;
    }
//    catch(
//    Exception e)
//
//    {
//        log.warn("设置TraceId异常：{}", ExceptionUtils.getStackTrace(e));
//    }


}
