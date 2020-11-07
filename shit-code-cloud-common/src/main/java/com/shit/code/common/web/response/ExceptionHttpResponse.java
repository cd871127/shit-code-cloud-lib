package com.shit.code.common.web.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Anthony
 * @date 11/1/20
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class ExceptionHttpResponse extends AbstractHttpResponse {

    private String exceptionClassName;


    public ExceptionHttpResponse(String code, String msg) {
        super(code, msg);
    }

    public ExceptionHttpResponse(HttpResponse httpResponse) {
        this(httpResponse.getCode(), httpResponse.getMsg());
    }

}
