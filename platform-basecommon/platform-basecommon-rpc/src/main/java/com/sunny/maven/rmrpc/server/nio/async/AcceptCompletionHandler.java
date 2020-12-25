package com.sunny.maven.rmrpc.server.nio.async;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/3/18 23:21
 * @description：异步接收通知
 * @modified By：
 * @version: 1.0.0
 */
public class AcceptCompletionHandler
        implements CompletionHandler<AsynchronousSocketChannel, RmRpcAsyncServer> {

    @Override
    public void completed(AsynchronousSocketChannel result, RmRpcAsyncServer attachment) {
        attachment.asynchronousServerSocketChannel.accept(attachment, this);
        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        result.read(readBuffer, readBuffer, new ReadCompletionHandler(result));
    }

    @Override
    public void failed(Throwable exc, RmRpcAsyncServer attachment) {
        exc.printStackTrace();
        attachment.latch.countDown();
    }
}
