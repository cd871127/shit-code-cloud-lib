package com.shit.code.common.web.response;

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
public class CommonHttpResponse extends GeneralHttpResponse<Object> {


    public CommonHttpResponse(HttpResponse httpResponse) {
        super(httpResponse);
    }

    public CommonHttpResponse(Object data) {
        super(data);
        setCode(HttpSuccessEnum.SUCCESS.getCode());
        setMsg(HttpSuccessEnum.SUCCESS.getMsg());
    }
}
