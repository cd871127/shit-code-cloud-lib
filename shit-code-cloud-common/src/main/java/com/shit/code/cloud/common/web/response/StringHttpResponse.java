package com.shit.code.cloud.common.web.response;

import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * @author anthonychen
 * @date 2020/10/15
 **/
@Slf4j
@NoArgsConstructor
@ToString(callSuper = true)
public class StringHttpResponse extends GeneralHttpResponse<String> {

    public StringHttpResponse(String code, String msg) {
        super(code, msg);
    }

    public StringHttpResponse(HttpResponse httpResponse) {
        super(httpResponse);
    }

    public StringHttpResponse(String data) {
        super(data);
    }
}
