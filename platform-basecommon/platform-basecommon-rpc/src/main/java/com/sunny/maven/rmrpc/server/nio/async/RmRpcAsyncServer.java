package com.sunny.maven.rmrpc.server.nio.async;

import com.sunny.maven.rmrpc.server.RmRpcServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/3/18 23:03
 * @description：异步服务器端
 * @modified By：
 * @version: 1.0.0
 */
public class RmRpcAsyncServer extends RmRpcServer {
    public CountDownLatch latch = new CountDownLatch(1);
    public AsynchronousServerSocketChannel asynchronousServerSocketChannel;

    /**
     * 启动rpc服务
     */
    public void start() {
        try {
            asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open();
            asynchronousServerSocketChannel.bind(new InetSocketAddress(port));
            asynchronousServerSocketChannel.accept(this, new AcceptCompletionHandler());
            latch.await();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public RmRpcAsyncServer(int port) {
        this.port = port;
    }
}
