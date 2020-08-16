package com.shit.code.cloud.common.web;


public interface Response<T> {
    Integer getCode();

    void setCode(Integer code);

    String getMessage();

    void setMessage(String message);

    T getData();

    void setData(T data);

}
