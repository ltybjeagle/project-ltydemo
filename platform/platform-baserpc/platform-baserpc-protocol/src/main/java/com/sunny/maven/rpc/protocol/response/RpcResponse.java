package com.sunny.maven.rpc.protocol.response;

import com.sunny.maven.rpc.protocol.base.RpcMessage;

/**
 * @author SUNNY
 * @description: RPC的响应类，对应的请求id在响应头中
 * @create: 2022-12-27 17:17
 */
public class RpcResponse extends RpcMessage {
    private static final long serialVersionUID = 1856647873158411999L;
    private String error;
    private Object result;

    public boolean isError() {
        return error != null;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
