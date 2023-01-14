package com.sunny.maven.rpc.protocol.enumeration;

/**
 * @author SUNNY
 * @description: RPC服务状态
 * @create: 2022-12-29 17:22
 */
public enum RpcStatus {
    SUCCESS(0),
    FAIL(1);

    private final int code;
    RpcStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
