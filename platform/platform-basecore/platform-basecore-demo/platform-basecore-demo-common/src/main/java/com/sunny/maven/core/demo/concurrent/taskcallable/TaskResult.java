package com.sunny.maven.core.demo.concurrent.taskcallable;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author SUNNY
 * @description: 任务执行结果
 * @create: 2023/7/10 18:54
 */
@Getter
@Setter
public class TaskResult implements Serializable {
    private static final long serialVersionUID = 8678277072402730062L;
    /**
     * 任务状态
     */
    private Integer taskStatus;
    /**
     * 任务消息
     */
    private String taskMessage;
    /**
     * 任务结果数据
     */
    private String taskResult;

    @Override
    public String toString() {
        return "TaskResult{" +
                "taskStatus=" + taskStatus +
                ", taskMessage='" + taskMessage + '\'' +
                ", taskResult='" + taskResult + '\'' +
                '}';
    }
}
