package com.shit.code.log.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.logging.LogLevel;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Anthony
 * @date 11/3/20
 **/
public abstract class AbstractAroundLogAspect {

    /**
     * 日志记录,保证如果同时存在两种aroundlog，只打印一次
     */
    protected static ThreadLocal<Set<String>> threadLocalLogRecord = ThreadLocal.withInitial(HashSet::new);

    /**
     * 获取日志等级
     *
     * @param joinPoint
     * @return
     */
    protected abstract LogLevel getLogLevel(ProceedingJoinPoint joinPoint, Method method);

    protected final Object proceed(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(joinPoint.getTarget().getClass().getName());
        stringBuilder.append("#");
        stringBuilder.append(method.getName());

        List<Class<?>> paramTypeList = Arrays.asList(method.getParameterTypes());

        if (!paramTypeList.isEmpty()) {
            paramTypeList.forEach(stringBuilder::append);
        }
        String fullMethodSignature = stringBuilder.toString();
        //判断高优先级的log有没有打出来 TODO anthony 添加参数签名
        Set<String> logRecord = threadLocalLogRecord.get();
        if (logRecord.contains(fullMethodSignature)) {
            //打出来了，直接返回
            return joinPoint.proceed();
        }
        //没有打出来，添加记录
        logRecord.add(fullMethodSignature);
        try {
            LogLevel logLevel = getLogLevel(joinPoint, method);
            Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
            printLog(logger, logLevel, "Method: {}, Parameter: {}", method.getName(), joinPoint.getArgs());
            long start = System.currentTimeMillis();
            Object result = joinPoint.proceed();
            printLog(logger, logLevel, "{Method: {}, Time: {}ms, Result: {} ", method.getName(), System.currentTimeMillis() - start, result);
            return result;
        } finally {
            //移除记录
            logRecord.remove(fullMethodSignature);
        }
    }

    /**
     * 打印日志
     *
     * @param logLevel
     * @param message
     * @param args
     */
    private void printLog(Logger log, LogLevel logLevel, String message, Object... args) {
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
