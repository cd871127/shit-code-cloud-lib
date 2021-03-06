package com.shit.code.common.web.response;

/**
 * @author anthonychen
 * @date 2020/10/15
 **/
public interface HttpResponse {
    /**
     * 0 请求成功
     *
     * @return
     */
    String getCode();

    /**
     * 失败才有这个消息
     *
     * @return
     */
    String getMsg();
}
