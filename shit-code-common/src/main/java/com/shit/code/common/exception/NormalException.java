package com.shit.code.common.exception;

import com.shit.code.common.web.response.HttpResponse;

/**
 * @author Anthony
 * @date 10/31/20
 **/
public class NormalException extends ShitCodeException {
    public NormalException(HttpResponse shitCodeExceptionEnum) {
        super(shitCodeExceptionEnum);
    }

    public NormalException(HttpResponse shitCodeExceptionEnum, Object... args) {
        super(shitCodeExceptionEnum, args);
    }
}
