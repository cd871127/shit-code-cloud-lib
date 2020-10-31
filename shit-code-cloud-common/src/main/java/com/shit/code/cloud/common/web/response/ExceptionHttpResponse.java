package com.shit.code.cloud.common.web.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Anthony
 * @date 11/1/20
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ExceptionHttpResponse extends AbstractHttpResponse {

    private String exceptionClassName;


    public ExceptionHttpResponse(String code, String msg) {
        super(code, msg);
    }

    public ExceptionHttpResponse(HttpResponse httpResponse) {
        this(httpResponse.getCode(), httpResponse.getMsg());
    }

}
