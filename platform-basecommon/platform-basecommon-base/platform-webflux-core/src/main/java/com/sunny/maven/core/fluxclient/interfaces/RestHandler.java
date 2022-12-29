package com.sunny.maven.core.fluxclient.interfaces;

import com.sunny.maven.core.fluxclient.beans.MethodInfo;
import com.sunny.maven.core.fluxclient.beans.ServerInfo;

/**
 * @author SUNNY
 * @description: Rest请求调用Handler
 * @create: 2022-09-30 10:09
 */
public interface RestHandler {
    /**
     * 初始化服务器信息
     * @param serverInfo
     */
    void init(ServerInfo serverInfo);

    /**
     * 调用Rest请求，返回结果
     * @param methodInfo
     * @return
     */
    Object invokeRest(MethodInfo methodInfo);
}
