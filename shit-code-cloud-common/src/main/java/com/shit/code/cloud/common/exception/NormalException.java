package com.shit.code.cloud.common.exception;

/**
 * @author Anthony
 * @date 10/31/20
 **/
public class NormalException extends ShitCodeException {
    public NormalException(ShitCodeExceptionEnum shitCodeExceptionEnum) {
        super(shitCodeExceptionEnum);
    }

    public NormalException(ShitCodeExceptionEnum shitCodeExceptionEnum, Object... args) {
        super(shitCodeExceptionEnum, args);
    }
}
