package com.sunny.maven.middle.scheduled.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author SUNNY
 * @description: 定时任务实体类
 * @create: 2022-11-12 11:50
 */
@Data
public class ScheduledJob {
    /**
     * 任务ID
     */
    private String jobId;
    /**
     * bean名称
     */
    private String beanName;
    /**
     * 方法名称
     */
    private String methodName;
    /**
     * 方法参数
     */
    private String methodParams;
    /**
     * cron表达式
     */
    private String cronExpression;
    /**
     * 状态（1正常 0暂停）
     */
    private Integer jobStatus;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}
