package com.sunny.maven.rmrpc.client.nio.sync;

import com.sunny.maven.rmrpc.client.RmRpcClient;
import com.sunny.maven.rmrpc.common.RmRpcRequest;
import com.sunny.maven.rmrpc.common.RmRpcResponse;
import com.sunny.maven.rmrpc.common.serialize.java.JavaSerialization;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/3/9 20:42
 * @description：链接服务器调用服务
 * @modified By：
 * @version: 1.0.0
 */
public class RmRpcSyncClient extends RmRpcClient {
    private boolean stop = false;

    public Object start() throws Throwable {
        Object rst = null;
        try (Selector selector = Selector.open()) {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            if (socketChannel.connect(new InetSocketAddress(host, port))) {
                socketChannel.register(selector, SelectionKey.OP_READ);
                doWrite(socketChannel, request);
            } else {
                socketChannel.register(selector, SelectionKey.OP_CONNECT);
            }
            while (!stop) {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectionKeys.iterator();
                SelectionKey key = null;
                while (it.hasNext()) {
                    key = it.next();
                    it.remove();
                    if (key.isValid()) {
                        if (key.isConnectable()) {
                            if (socketChannel.finishConnect()) {
                                socketChannel.register(selector, SelectionKey.OP_READ);
                                doWrite(socketChannel, request);
                            } else {
                                System.exit(1);
                            }
                        } else {
                            rst = handleInput(key);
                            if (rst != null) {
                                stop = true;
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rst;
    }

    /**
     *
     * @param key
     * @throws Exception
     */
    private Object handleInput(SelectionKey key) throws Exception {
        if (key.isReadable()) {
            SocketChannel channel = (SocketChannel) key.channel();
            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
            int readBytes = channel.read(readBuffer);
            if (readBytes > 0) {
                readBuffer.flip();
                byte[] bytes = new byte[readBuffer.remaining()];
                readBuffer.get(bytes);
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
                return response.getResult();
            } else if (readBytes < 0) {
                key.cancel();
                channel.close();
            }
        }
        return null;
    }

    /**
     *
     * @param channel
     * @param request
     * @throws IOException
     */
    private void doWrite(SocketChannel channel, RmRpcRequest request) throws IOException {
        JavaSerialization javaSerialization = JavaSerialization.getInstance();
        byte[] bytes = javaSerialization.objectToByte(request);
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
        writeBuffer.put(bytes);
        writeBuffer.flip();
        channel.write(writeBuffer);
    }

    public RmRpcSyncClient(String host, int port) {
        this.host = host;
        this.port = port;
    }
}
