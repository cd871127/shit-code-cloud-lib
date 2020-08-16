package com.shit.code.cloud.common.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebResponse<T> implements Response<T> {

    public WebResponse(T data) {
        this.data = data;
    }

    private Integer code = 0;

    private String message = "OK";

    private T data;


}
