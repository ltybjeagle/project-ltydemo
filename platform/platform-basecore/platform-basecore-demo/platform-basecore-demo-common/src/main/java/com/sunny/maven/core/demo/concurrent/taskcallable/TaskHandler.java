package com.sunny.maven.core.demo.concurrent.taskcallable;

/**
 * @author SUNNY
 * @description: 回调函数的实现类
 * @create: 2023/7/11 20:54
 */
public class TaskHandler implements TaskCallable<TaskResult> {
    @Override
    public TaskResult callable(TaskResult taskResult) {
        //TODO 拿到结果数据后进一步处理
        System.out.println(taskResult.toString());
        return taskResult;
    }
}
