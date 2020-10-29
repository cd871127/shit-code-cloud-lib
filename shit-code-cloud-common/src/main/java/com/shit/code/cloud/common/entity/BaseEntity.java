package com.shit.code.cloud.common.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Anthony
 * @date 10/29/20
 **/
@Data
public class BaseEntity {
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
