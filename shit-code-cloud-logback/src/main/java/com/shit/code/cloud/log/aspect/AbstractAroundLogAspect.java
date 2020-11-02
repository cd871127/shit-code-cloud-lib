package com.shit.code.cloud.log.aspect;

import com.shit.code.cloud.log.LogLevel;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author Anthony
 * @date 11/3/20
 **/
@Slf4j
public abstract class AbstractAroundLogAspect {

    /**
     * 获取日志等级
     *
     * @param joinPoint
     * @return
     */
    protected abstract LogLevel getLogLevel(ProceedingJoinPoint joinPoint);

    protected final Object proceed(ProceedingJoinPoint joinPoint) throws Throwable {
        LogLevel logLevel = getLogLevel(joinPoint);
        printLog(logLevel, "PARAMETER:{}", joinPoint.getArgs());
        try {
            long start = System.currentTimeMillis();
            Object result = joinPoint.proceed();
            printLog(logLevel, "RESULT:{} TIME:{}ms", result, System.currentTimeMillis() - start);
            return result;
        } catch (Throwable throwable) {
            throw throwable;
        }
    }

    /**
     * 打印日志
     *
     * @param logLevel
     * @param message
     * @param args
     */
    private void printLog(LogLevel logLevel, String message, Object... args) {
        switch (logLevel) {
            case ERROR:
                log.error(message, args);
                break;
            case DEBUG:
                log.debug(message, args);
                break;
            case WARN:
                log.warn(message, args);
                break;
            case INFO:
                log.info(message, args);
                break;
            case TRACE:
                log.trace(message, args);
                break;
            default:
                throw new IllegalArgumentException("非法的logLevel");
        }
    }

}
