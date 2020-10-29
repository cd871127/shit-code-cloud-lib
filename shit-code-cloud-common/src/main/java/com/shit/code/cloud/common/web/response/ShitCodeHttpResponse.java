package com.shit.code.cloud.common.web.response;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author anthonychen
 * @date 2020/10/14
 **/
@Slf4j
@Data
public class ShitCodeHttpResponse<T> implements HttpResponse{
    private int code = 0;
    private String msg;
    private T data;

    public ShitCodeHttpResponse() {

    }

    public ShitCodeHttpResponse(HttpResponse httpResponse) {
        this.code = httpResponse.getCode();
        this.msg = httpResponse.getMsg();
    }

    public ShitCodeHttpResponse(T data) {
        this.data = data;
    }
}
