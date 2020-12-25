package com.sunny.maven.rmrpc.common;

import java.io.Serializable;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/12/24 19:40
 * @description：rpc请求参数实体
 * @modified By：
 * @version: 1.0.0
 */
public class RmRpcRequest implements Serializable {
    private static final long serialVersionUID = 4305506463490924475L;
    private String className;
    private String methodName;
    private Class<?>[] paramTypes;
    private Object[] params;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParamTypes() {
        return paramTypes;
    }

    public void setParamTypes(Class<?>[] paramTypes) {
        this.paramTypes = paramTypes;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }
}
