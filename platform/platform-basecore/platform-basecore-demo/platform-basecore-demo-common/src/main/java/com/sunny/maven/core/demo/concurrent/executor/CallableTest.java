package com.sunny.maven.core.demo.concurrent.executor;

import java.util.concurrent.Callable;

/**
 * @author SUNNY
 * @description: 实现Callable实现线程
 * @create: 2023/5/24 16:59
 */
public class CallableTest implements Callable<Object> {
    /**
     * 支持返回值
     * @return Object
     * @throws Exception 异常
     */
    @Override
    public Object call() throws Exception {
        //TODO 在此写线程中执行的业务逻辑
        return null;
    }
}
