package com.shit.code.common.exception;

import com.shit.code.common.web.response.HttpResponse;

/**
 * @author Anthony
 * @date 10/31/20
 **/
public class CriticalException extends ShitCodeException {
    public CriticalException(HttpResponse shitCodeExceptionEnum) {
        super(shitCodeExceptionEnum);
    }

    public CriticalException(HttpResponse shitCodeExceptionEnum, Object... args) {
        super(shitCodeExceptionEnum, args);
    }
}
