package com.sunny.maven.rpc.disuse.defaultstrategy;

import com.sunny.maven.rpc.disuse.api.DisuseStrategy;
import com.sunny.maven.rpc.disuse.api.connection.ConnectionInfo;
import com.sunny.maven.rpc.spi.annotation.SPIClass;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author SUNNY
 * @description: 默认连接，获取列表中的第一个元素
 * @create: 2023-03-02 13:48
 */
@Slf4j
@SPIClass
public class DefaultDisuseStrategy implements DisuseStrategy {
    @Override
    public ConnectionInfo selectConnection(List<ConnectionInfo> connectionInfoList) {
        log.info("execute default disuse strategy...");
        return connectionInfoList.get(0);
    }
}
