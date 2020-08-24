package com.shit.code.cloud.mybatis.log;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;

/**
 * @author Anthony
 * @date 2020/4/6
 **/
@Intercepts(value = {
        @Signature(type = Executor.class,
                method = "update",
                args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class,
                method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class,
                        CacheKey.class, BoundSql.class})})
@Slf4j
public class SqlLogInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        String sqlId = null;
        try {
            MappedStatement mappedStatement = getArg(invocation, MappedStatement.class);
            sqlId = mappedStatement.getId();
            Object parameter = null;
            if (invocation.getArgs().length >= 1) {
                parameter = invocation.getArgs()[1];
            }
            // BoundSql就是封装myBatis最终产生的sql类
            BoundSql boundSql = mappedStatement.getBoundSql(parameter);
            // 获取节点的配置
            Configuration configuration = mappedStatement.getConfiguration();
            // 获取到最终的sql语句
            String sql = showSql(configuration, boundSql);
            log.debug("SqlId:{} \nExecutable Sql: {}", sqlId, sql);
        } catch (Exception e) {
            log.warn("show sql parameter failed. {}", ExceptionUtils.getStackTrace(e));
        }
        long start = System.currentTimeMillis();
        // 执行完上面的任务后，不改变原有的sql执行过程
        Object result = invocation.proceed();
        log.debug("SqlId:{} \nElapse:{}", sqlId, System.currentTimeMillis() - start);
        return result;
    }

    @SuppressWarnings("unchecked")
    protected <T> T getArg(Invocation invocation, Class<T> clazz) {
        for (Object o : invocation.getArgs()) {
            if (o.getClass().equals(clazz)) {
                return (T) o;
            }
        }
        return null;
    }

    /**
     * 如果参数是String，则添加单引号， 如果是日期，则转换为时间格式器并加单引号；
     * 对参数是null和不是null的情况作了处理
     *
     * @param obj
     * @return
     */
    private String getParameterValue(Object obj) {
        String value;
        if (obj == null) {
            value = "null";
        } else if (obj instanceof String) {
            value = "'" + obj.toString() + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(new Date()) + "'";
        } else {
            value = obj.toString();
        }
        return value;
    }

    /**
     * 进行？的替换
     *
     * @param configuration
     * @param boundSql
     * @return
     */
    private String showSql(Configuration configuration, BoundSql boundSql) {
        // 获取参数
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        // sql语句中多个空格都用一个空格代替
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (CollectionUtils.isNotEmpty(parameterMappings) && parameterObject != null) {
            // 获取类型处理器注册器，类型处理器的功能是进行java类型和数据库类型的转换
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            // 如果根据parameterObject.getClass(）可以找到对应的类型，则替换
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(parameterObject)));
            } else {
                // MetaObject主要是封装了originalObject对象，提供了get和set的方法用于获取和设置originalObject的属性值,主要支持对JavaBean、Collection、Map三种类型对象的操作
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(obj)));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        // 该分支是动态sql
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(obj)));
                    } else {
                        //打印出缺失，提醒该参数缺失并防止错位
                        sql = sql.replaceFirst("\\?", "缺失");
                    }
                }
            }
        }
        return sql;
    }

}
