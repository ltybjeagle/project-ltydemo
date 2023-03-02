package com.sunny.maven.rpc.threadpool;

import com.sunny.maven.rpc.constants.RpcConstants;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author SUNNY
 * @description: 流量后置处理器线程池
 * @create: 2023-03-01 13:44
 */
public class FlowPostProcessorThreadPool {

    private static ThreadPoolExecutor threadPoolExecutor;
    static {
        threadPoolExecutor = new ThreadPoolExecutor(8, 8,
                RpcConstants.DEFAULT_KEEP_ALIVE_TIME, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(RpcConstants.DEFAULT_QUEUE_CAPACITY));
    }

    public static void submit(Runnable task) {
        threadPoolExecutor.submit(task);
    }

    public static void stop() {
        threadPoolExecutor.shutdown();
    }

}
