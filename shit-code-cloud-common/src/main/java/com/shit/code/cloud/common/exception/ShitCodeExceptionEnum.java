package com.shit.code.cloud.common.exception;

import com.shit.code.cloud.common.web.response.HttpResponse;
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
    OK(0, ""),
    FAILED(1, "请求失败");

    private final int code;
    private final String msg;

    ShitCodeExceptionEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
