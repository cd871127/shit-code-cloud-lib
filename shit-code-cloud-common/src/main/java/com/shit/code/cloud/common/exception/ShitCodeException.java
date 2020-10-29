package com.shit.code.cloud.common.exception;

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
public class ShitCodeException extends RuntimeException {

    private ShitCodeExceptionEnum shitCodeExceptionEnum;

    public ShitCodeException(ShitCodeExceptionEnum shitCodeExceptionEnum) {
        super();
        this.shitCodeExceptionEnum = shitCodeExceptionEnum;
    }
}
