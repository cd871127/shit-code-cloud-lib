package com.shit.code.cloud.common.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseDTO {
    private Integer uniqueId;
    private Integer version;
    private Status status = Status.INVALID;
    private String createBy;
    private LocalDateTime createTime;
    private String updateBy;
    private LocalDateTime updateTime;

    public enum Status {
        INVALID, VALID, DELETED
    }
}