package com.sunny.maven.rmrpc.common;

import java.io.Serializable;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/12/24 20:39
 * @description：Rpc响应类
 * @modified By：
 * @version: 1.0.0
 */
public class RmRpcResponse implements Serializable {
    private static final long serialVersionUID = 4813833625434006790L;
    private Throwable error;
    private Object result;

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
