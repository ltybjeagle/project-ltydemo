package com.sunny.maven.rmrpc.server.nio.sync;

import com.sunny.maven.rmrpc.common.RmRpcRequest;
import com.sunny.maven.rmrpc.common.RmRpcResponse;
import com.sunny.maven.rmrpc.common.serialize.java.JavaSerialization;
import com.sunny.maven.rmrpc.server.RmRpcServer;
import com.sunny.maven.rmrpc.server.RmRpcServiceContainer;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/3/9 20:20
 * @description：服务器端
 * @modified By：
 * @version: 1.0.0
 */
public class RmRpcSyncServer extends RmRpcServer {
    /**
     * 启动rpc服务
     */
    public void start() {
        try (Selector selector = Selector.open()) {
            ServerSocketChannel socketChannel = ServerSocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.socket().bind(new InetSocketAddress(this.port), 1024);
            socketChannel.register(selector, SelectionKey.OP_ACCEPT);

//            // 创建线程池
//            Executor executor = new ThreadPoolExecutor(5, 10, 10,
//                    TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
            while (true) {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectionKeys.iterator();
                SelectionKey key = null;
                while (it.hasNext()) {
                    key = it.next();
                    it.remove();
                    if (key.isValid()) {
                        if (key.isAcceptable()) {
                            ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                            SocketChannel sc = ssc.accept();
                            sc.configureBlocking(false);
                            sc.register(selector, SelectionKey.OP_READ);
                        }
                        if (key.isReadable()) {
                            RmRpcResponse response = new RmRpcResponse();
                            try {
                                SocketChannel sc = (SocketChannel) key.channel();
                                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                                int readBytes = sc.read(readBuffer);
                                if (readBytes > 0) {
                                    readBuffer.flip();
                                    byte[] bytes = new byte[readBuffer.remaining()];
                                    readBuffer.get(bytes);
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
                                        Object result = method.invoke(service, request.getParams());
                                        // 得到结果并返回
                                        response.setResult(result);
                                    }
                                    doWrite(sc, response);
                                } else if (readBytes < 0) {
                                    key.cancel();
                                    sc.close();
                                }
                            } catch (Exception ex) {
                                key.cancel();
                                if (key.channel() != null) {
                                    key.channel().close();
                                }
                            }
                        }
                    } else {
                        if (key != null) {
                            key.cancel();
                            if (key.channel() != null) {
                                key.channel().close();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param channel
     * @param response
     * @throws IOException
     */
    private void doWrite(SocketChannel channel, RmRpcResponse response) throws IOException {
        JavaSerialization javaSerialization = JavaSerialization.getInstance();
        byte[] bytes = javaSerialization.objectToByte(response);
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
        writeBuffer.put(bytes);
        writeBuffer.flip();
        channel.write(writeBuffer);
    }

    public RmRpcSyncServer(int port) {
        this.port = port;
    }
}
