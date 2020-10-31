package com.shit.code.cloud.mybatis.exception;

import com.shit.code.cloud.common.exception.ShitCodeException;
import com.shit.code.cloud.common.exception.ShitCodeExceptionEnum;

/**
 * @author Anthony
 * @date 10/31/20
 **/
public class DataNotFoundException extends ShitCodeException {


    public DataNotFoundException(ShitCodeExceptionEnum shitCodeExceptionEnum) {
        super(shitCodeExceptionEnum);
    }

    public DataNotFoundException(ShitCodeExceptionEnum shitCodeExceptionEnum, Object... args) {
        super(shitCodeExceptionEnum, args);
    }
}
