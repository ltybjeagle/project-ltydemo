package com.sunny.maven.rmrpc.enums;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/3/19 14:56
 * @description：RPC服务类型
 * @modified By：
 * @version: 1.0.0
 */
public enum RmRpcEnum {
    /**
     * NIO非阻塞模式
     */
    SYNC_SERVER("syncServer"),
    /**
     * NIO2异步非阻塞模式
     */
    ASYNC_SERVER("asyncServer");

    private String serverType;
    RmRpcEnum(String serverType) {
        this.serverType = serverType;
    }

    public String getServerType() {
        return serverType;
    }

    public static RmRpcEnum getEnum(String serverType) {
        for (RmRpcEnum rmRpcEnum : RmRpcEnum.values()) {
            if (rmRpcEnum.getServerType().equalsIgnoreCase(serverType)) {
                return rmRpcEnum;
            }
        }
        return null;
    }
}
