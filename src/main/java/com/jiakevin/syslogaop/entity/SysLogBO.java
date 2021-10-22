package com.jiakevin.syslogaop.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author jiakevin
 * @since 2021-10-21 23:01
 */
@Data
public class SysLogBO {
    private String className;

    private String methodName;

    private String params;

    private Long execTime;

    private String remark;

    private LocalDateTime createDate;
}
