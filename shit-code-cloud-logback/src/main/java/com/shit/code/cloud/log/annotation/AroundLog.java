package com.shit.code.cloud.log.annotation;

import com.shit.code.cloud.log.LogLevel;

/**
 * @author Anthony
 * @date 11/3/20
 **/
public @interface AroundLog {

    LogLevel level() default LogLevel.DEBUG;

}
