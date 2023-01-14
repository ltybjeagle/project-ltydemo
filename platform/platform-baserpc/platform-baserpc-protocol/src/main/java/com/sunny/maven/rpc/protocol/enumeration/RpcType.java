package com.sunny.maven.rpc.protocol.enumeration;

/**
 * @author SUNNY
 * @description: 协议的类型
 * @create: 2022-12-27 16:42
 */
public enum RpcType {
    // 请求消息
    REQUEST(1),
    // 响应消息
    RESPONSE(2),
    // 心跳数据
    HEARTBEAT(3);
    private final int type;

    RpcType(int type) {
        this.type = type;
    }

    public static RpcType findByType(int type) {
        for (RpcType rpcType : RpcType.values()) {
            if (rpcType.getType() == type) {
                return rpcType;
            }
        }
        return null;
    }

    public int getType() {
        return type;
    }
}
