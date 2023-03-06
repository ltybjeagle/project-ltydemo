package com.sunny.maven.rpc.buffer.cache;

import com.sunny.maven.rpc.common.exception.RpcException;
import com.sunny.maven.rpc.constants.RpcConstants;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author SUNNY
 * @description: 缓冲区实现
 * @create: 2023-03-04 14:47
 */
public class BufferCacheManager<T> {
    /**
     * 缓冲队列
     */
    private BlockingQueue<T> bufferQueue;
    /**
     * 缓存管理器单例对象
     */
    private static volatile BufferCacheManager instance;

    /**
     * 私有构造方法
     */
    private BufferCacheManager(int bufferSize) {
        if (bufferSize <= 0) {
            bufferSize = RpcConstants.DEFAULT_BUFFER_SIZE;
        }
        this.bufferQueue = new ArrayBlockingQueue<>(bufferSize);
    }

    /**
     * 创建单例对象
     */
    public static <T> BufferCacheManager<T> getInstance(int bufferSize) {
        if (instance == null) {
            synchronized (BufferCacheManager.class) {
                if (instance == null) {
                    instance = new BufferCacheManager(bufferSize);
                }
            }
        }
        return instance;
    }

    /**
     * 向缓冲区添加元素
     */
    public void put(T t) {
        try {
            this.bufferQueue.put(t);
        } catch (InterruptedException e) {
            throw new RpcException(e);
        }
    }

    /**
     * 获取缓冲区元素
     */
    public T take() {
        try {
            return this.bufferQueue.take();
        } catch (InterruptedException e) {
            throw new RpcException(e);
        }
    }
}
