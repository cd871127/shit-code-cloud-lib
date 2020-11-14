package com.shit.code.common.web.response;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author anthonychen
 * @date 2020/10/14
 **/
@Slf4j
@Getter
public enum HttpSuccessEnum implements HttpResponse {


    /**
     * 请求ok
     */
    SUCCESS("00000000", "请求成功");
    /**
     * 0-2位，系统代码
     * 3-4位，异常等级
     * 5-7位，系统自定义
     */
    private final String code;
    private final String msg;

    HttpSuccessEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
