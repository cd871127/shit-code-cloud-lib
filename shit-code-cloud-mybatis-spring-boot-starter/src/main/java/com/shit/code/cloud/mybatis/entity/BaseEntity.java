package com.shit.code.cloud.mybatis.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Anthony
 * @date 10/31/20
 **/
@Data
public class BaseEntity {

    @TableId(type = IdType.AUTO, value = "id")
    private Long id;

    @Version
    @TableField(update = "%s+1", updateStrategy = FieldStrategy.IGNORED)
    private Integer version;

    private BaseEntity.DataStatus dataStatus;

    private String createBy;

    private LocalDateTime createTime;

    private String updateBy;

    @TableField(update = "now()", updateStrategy = FieldStrategy.IGNORED)
    private LocalDateTime updateTime;

    @TableLogic(value = "'NO'", delval = "'YES'")
    private Deleted deleted;

    public enum DataStatus {
        /**
         * 数据状态
         */
        INVALID, VALID
    }

    public enum Deleted {
        /**
         * 是否被删除
         */
        NO, YES
    }
}
