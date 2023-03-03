package com.sunny.maven.rpc.disuse.api;

import com.sunny.maven.rpc.constants.RpcConstants;
import com.sunny.maven.rpc.disuse.api.connection.ConnectionInfo;
import com.sunny.maven.rpc.spi.annotation.SPI;

import java.util.List;

/**
 * @author SUNNY
 * @description: 淘汰策略
 * @create: 2023-03-02 12:09
 */
@SPI(value = RpcConstants.RPC_CONNECTION_DISUSE_STRATEGY_DEFAULT)
public interface DisuseStrategy {

    /**
     * 从连接列表中根据规则获取一个连接对象
     */
    ConnectionInfo selectConnection(List<ConnectionInfo> connectionInfoList);
}
