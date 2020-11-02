package com.shit.code.cloud.common.web.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * @author anthonychen
 * @date 2020/10/14
 **/
@EqualsAndHashCode(callSuper = true)
@Slf4j
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class GeneralHttpResponse<T> extends AbstractHttpResponse {

    private T data;

    public GeneralHttpResponse(String code, String msg) {
        super(code, msg);
    }

    public GeneralHttpResponse(HttpResponse httpResponse) {
        super(httpResponse);
    }

    public GeneralHttpResponse(T data) {
        this.data = data;
    }
}
