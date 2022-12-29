package com.sunny.maven.rpc.protocol.enumeration;

/**
 * @author SUNNY
 * @description: 协议的类型
 * @create: 2022-12-27 16:42
 */
public enum RcpType {
    // 请求消息
    REQUEST(1),
    // 响应消息
    RESPONSE(2),
    // 心跳数据
    HEARTBEAT(3);
    private final int type;

    RcpType(int type) {
        this.type = type;
    }

    public static RcpType findByType(int type) {
        for (RcpType rcpType : RcpType.values()) {
            if (rcpType.getType() == type) {
                return rcpType;
            }
        }
        return null;
    }

    public int getType() {
        return type;
    }
}
