package com.sunny.maven.rpc.provider.common.handler;

import com.sunny.maven.rpc.common.helper.RpcServiceHelper;
import com.sunny.maven.rpc.common.threadpool.ServerThreadPool;
import com.sunny.maven.rpc.constants.RpcConstants;
import com.sunny.maven.rpc.protocol.RpcProtocol;
import com.sunny.maven.rpc.protocol.enumeration.RpcStatus;
import com.sunny.maven.rpc.protocol.enumeration.RpcType;
import com.sunny.maven.rpc.protocol.header.RpcHeader;
import com.sunny.maven.rpc.protocol.request.RpcRequest;
import com.sunny.maven.rpc.protocol.response.RpcResponse;
import com.sunny.maven.rpc.reflect.api.ReflectInvoker;
import com.sunny.maven.rpc.spi.loader.ExtensionLoader;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author SUNNY
 * @description: RPC服务提供者的Handler处理类
 * @create: 2022-12-26 17:29
 */
@Slf4j
public class RpcProviderHandler extends SimpleChannelInboundHandler<RpcProtocol<RpcRequest>> {
    /**
     * 存储服务名称#版本号#分组与对象实例的映射关系
     */
    private final Map<String, Object> handlerMap;
    /**
     * 调用采用哪种类型调用真实方法
     */
    private ReflectInvoker reflectInvoker;
    public RpcProviderHandler(String reflectType, Map<String, Object> handlerMap) {
        this.handlerMap = handlerMap;
        this.reflectInvoker = ExtensionLoader.getExtension(ReflectInvoker.class, reflectType);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcProtocol<RpcRequest> protocol) throws Exception {
        ServerThreadPool.submit(() -> {
            RpcProtocol<RpcResponse> responseRpcProtocol = handlerMessage(protocol);
            ctx.writeAndFlush(responseRpcProtocol).addListener((ChannelFutureListener) channelFuture ->
                    log.debug("Send response for request " + protocol.getHeader().getRequestId()));
        });
    }

    /**
     * 处理消息
     */
    private RpcProtocol<RpcResponse> handlerMessage(RpcProtocol<RpcRequest> protocol) {
        RpcProtocol<RpcResponse> responseRpcProtocol = null;
        RpcHeader header = protocol.getHeader();
        if (header.getMsgType() == (byte) RpcType.HEARTBEAT_FROM_CONSUMER.getType()) {
            // 心跳消息
            responseRpcProtocol = handlerHeartbeatMessage(protocol, header);
        } else if (header.getMsgType() == (byte) RpcType.REQUEST.getType()) {
            // 请求消息
            responseRpcProtocol = handlerRequestMessage(protocol, header);
        }
        return responseRpcProtocol;
    }

    /**
     * 处理心跳消息
     */
    private RpcProtocol<RpcResponse> handlerHeartbeatMessage(RpcProtocol<RpcRequest> protocol, RpcHeader header) {
        header.setMsgType((byte) RpcType.HEARTBEAT_TO_CONSUMER.getType());
        RpcRequest request = protocol.getBody();
        RpcProtocol<RpcResponse> responseRpcProtocol = new RpcProtocol<>();
        RpcResponse response = new RpcResponse();
        response.setResult(RpcConstants.HEARTBEAT_PONG);
        response.setOneway(request.isOneway());
        response.setAsync(request.isAsync());
        header.setStatus((byte) RpcStatus.SUCCESS.getCode());
        responseRpcProtocol.setHeader(header);
        responseRpcProtocol.setBody(response);
        return responseRpcProtocol;
    }

    /**
     * 处理请求消息
     */
    private RpcProtocol<RpcResponse> handlerRequestMessage(RpcProtocol<RpcRequest> protocol, RpcHeader header) {
        header.setMsgType((byte) RpcType.RESPONSE.getType());
        RpcRequest request = protocol.getBody();
        log.debug("Receive request " + header.getRequestId());
        RpcProtocol<RpcResponse> responseRpcProtocol = new RpcProtocol<>();
        RpcResponse response = new RpcResponse();
        try {
            Object result = handler(request);
            response.setResult(result);
            response.setOneway(request.isOneway());
            response.setAsync(request.isAsync());
            header.setStatus((byte) RpcStatus.SUCCESS.getCode());
        } catch (Throwable t) {
            response.setError(t.toString());
            header.setStatus((byte) RpcStatus.FAIL.getCode());
            log.error("RPC Server handle request error",t);
        }
        responseRpcProtocol.setHeader(header);
        responseRpcProtocol.setBody(response);
        return responseRpcProtocol;
    }

    private Object handler(RpcRequest request) throws Throwable {
        String serviceKey = RpcServiceHelper.buildServiceKey(request.getClassName(), request.getVersion(),
                request.getGroup());
        Object serviceBean = handlerMap.get(serviceKey);
        if (serviceBean == null) {
            throw new RuntimeException(String.format("service not exist: %s:%s", request.getClassName(),
                    request.getMethodName()));
        }
        Class<?> serviceClass = serviceBean.getClass();
        String methodName = request.getMethodName();
        Class<?>[] parameterTypes = request.getParameterTypes();
        Object[] parameters = request.getParameters();

        log.debug(serviceClass.getName());
        log.debug(methodName);
        if (parameterTypes != null && parameterTypes.length > 0) {
            for (Class<?> parameterType : parameterTypes) {
                log.debug(parameterType.getName());
            }
        }
        if (parameters != null && parameters.length > 0) {
            for (Object parameter : parameters) {
                log.debug(parameter.toString());
            }
        }
        return this.reflectInvoker.invokeMethod(serviceBean, serviceClass, methodName, parameterTypes, parameters);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("server caught exception", cause);
        ctx.close();
    }
}
