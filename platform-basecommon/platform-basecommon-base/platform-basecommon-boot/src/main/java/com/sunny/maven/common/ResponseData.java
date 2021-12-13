package com.sunny.maven.common;

/**
 * @author SUNNY
 * @description: 响应结果
 * @create: 2021-11-23 10:42
 */
public class ResponseData {
    private Boolean status = true;
    private int code = 200;
    private String message;
    private Object data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
