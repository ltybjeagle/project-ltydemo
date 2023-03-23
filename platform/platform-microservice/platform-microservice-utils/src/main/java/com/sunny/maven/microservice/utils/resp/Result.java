package com.sunny.maven.microservice.utils.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author SUNNY
 * @description: 返回的结果数据
 * @create: 2023-03-22 11:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1294910355328315725L;
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 状态描述
     */
    private String codeMsg;
    /**
     * 返回的数据
     */
    private T data;

}
