package com.shit.code.common.exception;

import com.shit.code.common.web.response.HttpResponse;

/**
 * @author Anthony
 * @date 10/31/20
 **/
public class NormalException extends BusinessException {
    public NormalException(HttpResponse businessExceptionEnum) {
        super(businessExceptionEnum);
    }

    public NormalException(HttpResponse businessExceptionEnum, Object... args) {
        super(businessExceptionEnum, args);
    }
}
