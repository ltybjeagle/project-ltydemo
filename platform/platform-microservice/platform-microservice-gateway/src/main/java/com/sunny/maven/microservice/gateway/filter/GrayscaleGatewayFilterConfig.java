package com.sunny.maven.microservice.gateway.filter;

import lombok.Data;

import java.io.Serializable;

/**
 * @author SUNNY
 * @description: 接收配置参数
 * @create: 2023/7/12 18:38
 */
@Data
public class GrayscaleGatewayFilterConfig implements Serializable {
    private static final long serialVersionUID = 983019309000445082L;
    private boolean grayscale;
}
