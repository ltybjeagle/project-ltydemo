package com.sunny.maven.core.fluxclient.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author SUNNY
 * @description: 服务器信息类
 * @create: 2022-09-29 17:59
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServerInfo {
    /**
     * 服务器url
     */
    private String url;
}
