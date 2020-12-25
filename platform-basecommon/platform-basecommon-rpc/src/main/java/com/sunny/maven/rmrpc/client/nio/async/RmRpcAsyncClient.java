package com.sunny.maven.rmrpc.client.nio.async;

import com.sunny.maven.rmrpc.client.RmRpcClient;
import com.sunny.maven.rmrpc.common.RmRpcResponse;
import com.sunny.maven.rmrpc.common.serialize.java.JavaSerialization;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/12/24 20:51
 * @description：RPC服务异步消费端
 * @modified By：
 * @version: 1.0.0
 */
public class RmRpcAsyncClient extends RmRpcClient
        implements CompletionHandler<Void, RmRpcAsyncClient> {
    public CountDownLatch latch = new CountDownLatch(1);
    public AsynchronousSocketChannel clientChannel;
    public Object resultObj;

    @Override
    public Object start() throws Throwable {
        try {
            clientChannel = AsynchronousSocketChannel.open();
            clientChannel.connect(new InetSocketAddress(host, port), this, this);
            latch.await();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            clientChannel.close();
        }
        return resultObj;
    }

    @Override
    public void completed(Void result, RmRpcAsyncClient attachment) {
        JavaSerialization javaSerialization = JavaSerialization.getInstance();
        byte[] bytes = javaSerialization.objectToByte(request);
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
        writeBuffer.put(bytes);
        writeBuffer.flip();
        clientChannel.write(writeBuffer, writeBuffer, new CompletionHandler<Integer, ByteBuffer>() {

            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                if (attachment.hasRemaining()) {
                    clientChannel.write(attachment, attachment, this);
                } else {
                    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                    clientChannel.read(readBuffer, readBuffer, new CompletionHandler<Integer, ByteBuffer>() {

                        @Override
                        public void completed(Integer result, ByteBuffer attachment) {
                            attachment.flip();
                            byte[] bytes = new byte[attachment.remaining()];
                            attachment.get(bytes);
                            JavaSerialization javaSerialization = JavaSerialization.getInstance();
                            Object res = javaSerialization.byteToObject(bytes);
                            RmRpcResponse response = null;
                            if (!(res instanceof RmRpcResponse)) {
                                throw new RuntimeException("返回参数不正确");
                            } else {
                                response = (RmRpcResponse) res;
                            }
                            // 返回结果
                            if (response.getError() != null) { // 服务器产生异常
                                throw new RuntimeException("服务端处理异常");
                            }
                            resultObj = response.getResult();
                            latch.countDown();
                        }

                        @Override
                        public void failed(Throwable exc, ByteBuffer attachment) {
                            try {
                                clientChannel.close();
                                latch.countDown();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                try {
                    clientChannel.close();
                    latch.countDown();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void failed(Throwable exc, RmRpcAsyncClient attachment) {
        exc.printStackTrace();
        try {
            clientChannel.close();
            latch.countDown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public RmRpcAsyncClient(String host, int port) {
        this.host = host;
        this.port = port;
    }
}
