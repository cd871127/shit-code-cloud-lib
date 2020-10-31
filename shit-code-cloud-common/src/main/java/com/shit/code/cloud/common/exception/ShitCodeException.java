package com.shit.code.cloud.common.exception;

import com.shit.code.cloud.common.web.response.HttpResponse;
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
public class ShitCodeException extends RuntimeException implements HttpResponse {

    private String code;

    private String msg;

    public ShitCodeException(ShitCodeExceptionEnum shitCodeExceptionEnum) {
        super(shitCodeExceptionEnum.getMsg());
        this.code = shitCodeExceptionEnum.getCode();
        this.msg = super.getMessage();
    }

    public ShitCodeException(ShitCodeExceptionEnum shitCodeExceptionEnum, Object... args) {
        super(String.format(shitCodeExceptionEnum.getMsg(), args));
        this.code = shitCodeExceptionEnum.getCode();
        this.msg = super.getMessage();
    }
}
