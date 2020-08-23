package com.shit.code.cloud.common.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseDTO {
    private Integer uniqueId;
    private Integer version;
    private Status status;
    private String createBy;
    private LocalDateTime createTime;
    private String updateBy;
    private LocalDateTime updateTime;

    public enum Status {
        /**
         * 数据状态
         */
        INVALID, VALID, DELETED
    }
}