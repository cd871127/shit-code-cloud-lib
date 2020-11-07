package com.shit.code.log.annotation;

import com.shit.code.log.LogLevel;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author Anthony
 * @date 11/3/20
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AroundLog {

    @AliasFor("level")
    LogLevel value() default LogLevel.DEBUG;

    @AliasFor("value")
    LogLevel level() default LogLevel.DEBUG;

}
