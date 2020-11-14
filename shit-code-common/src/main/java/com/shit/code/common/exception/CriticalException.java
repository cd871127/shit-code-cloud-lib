package com.shit.code.common.exception;

import com.shit.code.common.web.response.HttpResponse;

/**
 * @author Anthony
 * @date 10/31/20
 **/
public class CriticalException extends BusinessException {
    public CriticalException(HttpResponse businessExceptionEnum) {
        super(businessExceptionEnum);
    }

    public CriticalException(HttpResponse businessExceptionEnum, Object... args) {
        super(businessExceptionEnum, args);
    }
}
