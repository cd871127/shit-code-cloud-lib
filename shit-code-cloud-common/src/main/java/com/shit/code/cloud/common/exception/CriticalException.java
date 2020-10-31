package com.shit.code.cloud.common.exception;

/**
 * @author Anthony
 * @date 10/31/20
 **/
public class CriticalException extends ShitCodeException{
    public CriticalException(ShitCodeExceptionEnum shitCodeExceptionEnum) {
        super(shitCodeExceptionEnum);
    }

    public CriticalException(ShitCodeExceptionEnum shitCodeExceptionEnum, Object... args) {
        super(shitCodeExceptionEnum, args);
    }
}
