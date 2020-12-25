package com.sunny.maven.rmrpc.server.nio.async;

import com.sunny.maven.rmrpc.common.RmRpcRequest;
import com.sunny.maven.rmrpc.common.RmRpcResponse;
import com.sunny.maven.rmrpc.common.serialize.java.JavaSerialization;
import com.sunny.maven.rmrpc.server.RmRpcServiceContainer;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/3/18 23:34
 * @description：异步读取通知
 * @modified By：
 * @version: 1.0.0
 */
public class ReadCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {
    public AsynchronousSocketChannel channel;
    public ReadCompletionHandler(AsynchronousSocketChannel channel) {
        if (this.channel == null) {
            this.channel = channel;
        }
    }
    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        RmRpcResponse response = new RmRpcResponse();
        try {
            attachment.flip();
            byte[] bytes = new byte[attachment.remaining()];
            attachment.get(bytes);
            JavaSerialization javaSerialization = JavaSerialization.getInstance();
            Object param = javaSerialization.byteToObject(bytes);
            if (!(param instanceof RmRpcRequest)) {
                response.setError(new Exception("参数错误"));
            } else {
                RmRpcRequest request = (RmRpcRequest) param;
                // 查找并执行服务方法
                RmRpcServiceContainer rmRpcServiceContainer = RmRpcServiceContainer.getInstance();
                Object service = rmRpcServiceContainer.getRmRpcService(request);
                Class<?> serviceClazz= service.getClass();
                Method method = serviceClazz.getMethod(request.getMethodName(), request.getParamTypes());
                Object rst = method.invoke(service, request.getParams());
                // 得到结果并返回
                response.setResult(rst);
            }
            doWrite(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param response
     */
    private void doWrite(RmRpcResponse response) {
        JavaSerialization javaSerialization = JavaSerialization.getInstance();
        byte[] bytes = javaSerialization.objectToByte(response);
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
        writeBuffer.put(bytes);
        writeBuffer.flip();
        channel.write(writeBuffer, writeBuffer, new CompletionHandler<Integer, ByteBuffer>() {

            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                if (attachment.hasRemaining()) {
                    channel.write(attachment, attachment, this);
                }
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        try {
            this.channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
