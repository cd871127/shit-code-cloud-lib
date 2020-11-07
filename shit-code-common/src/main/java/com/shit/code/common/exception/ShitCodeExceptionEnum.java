package com.shit.code.common.exception;

import com.shit.code.common.web.response.HttpResponse;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author anthonychen
 * @date 2020/10/14
 **/
@Slf4j
@Getter
public enum ShitCodeExceptionEnum implements HttpResponse {


    /**
     * 请求ok
     */
    OK("00000000", "请求成功"),

    FAILED("00000001", "请求失败:%s"),

    ERR_DATA_NOT_EXIST("00000002", "数据不存在,key:%s"),

    ERR_INSERT_COUNT("00000003", "插入数据量异常，预期:%s, 实际:%s"),

    ERR_DELETE_COUNT("00000004", "删除数据量异常，预期:%s, 实际:%s"),
    ERR_UPDATE_COUNT("00000005", "更新数据量异常，预期:%s, 实际:%s"),
    ;
    /**
     * 0-2位，系统代码
     * 3-4位，异常等级
     * 5-7位，系统自定义
     */
    private final String code;
    private final String msg;

    ShitCodeExceptionEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
