package com.sunny.maven.core.demo.concurrent.taskcallable;

/**
 * @author SUNNY
 * @description: 测试回调
 * @create: 2023/7/11 21:04
 */
public class TaskCallableTest {

    public static void main(String[] args) {
        TaskCallable<TaskResult> taskCallable = new TaskHandler();
        TaskExecutor taskExecutor = new TaskExecutor(taskCallable, "测试回调任务");
        new Thread(taskExecutor).start();
    }
}
