package com.sunny.maven.rpc.disuse.last;

import com.sunny.maven.rpc.disuse.api.DisuseStrategy;
import com.sunny.maven.rpc.disuse.api.connection.ConnectionInfo;
import com.sunny.maven.rpc.spi.annotation.SPIClass;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author SUNNY
 * @description: 获取连接列表中最后一个连接信息
 * @create: 2023-03-03 14:11
 */
@Slf4j
@SPIClass
public class LastDisuseStrategy implements DisuseStrategy {
    @Override
    public ConnectionInfo selectConnection(List<ConnectionInfo> connectionInfoList) {
        log.info("execute last disuse strategy...");
        if (connectionInfoList.isEmpty()) {
            return null;
        }
        return connectionInfoList.get(connectionInfoList.size() - 1);
    }
}
