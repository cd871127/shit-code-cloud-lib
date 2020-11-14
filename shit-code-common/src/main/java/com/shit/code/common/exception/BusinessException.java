package com.shit.code.common.exception;

import com.shit.code.common.web.response.HttpResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * @author anthonychen
 * @date 2020/10/14
 **/
@EqualsAndHashCode(callSuper = true)
@Slf4j
@Data
public class BusinessException extends RuntimeException implements HttpResponse {

    private String code;

    private String msg;

    public BusinessException(HttpResponse businessExceptionEnum) {
        super(businessExceptionEnum.getMsg());
        this.code = businessExceptionEnum.getCode();
        this.msg = super.getMessage();
    }

    public BusinessException(HttpResponse businessExceptionEnum, Object... args) {
        super(String.format(businessExceptionEnum.getMsg(), args));
        this.code = businessExceptionEnum.getCode();
        this.msg = super.getMessage();
    }
}
