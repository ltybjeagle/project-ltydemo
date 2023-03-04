package com.sunny.maven.rpc.disuse.first;

import com.sunny.maven.rpc.disuse.api.DisuseStrategy;
import com.sunny.maven.rpc.disuse.api.connection.ConnectionInfo;
import com.sunny.maven.rpc.spi.annotation.SPIClass;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author SUNNY
 * @description: 获取列表中的第一个
 * @create: 2023-03-03 10:46
 */
@Slf4j
@SPIClass
public class FirstDisuseStrategy implements DisuseStrategy {
    @Override
    public ConnectionInfo selectConnection(List<ConnectionInfo> connectionInfoList) {
        log.info("execute first disuse strategy...");
        return connectionInfoList.get(0);
    }
}
