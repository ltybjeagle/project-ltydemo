package com.sunny.maven.rpc.disuse.random;

import com.sunny.maven.rpc.disuse.api.DisuseStrategy;
import com.sunny.maven.rpc.disuse.api.connection.ConnectionInfo;
import com.sunny.maven.rpc.spi.annotation.SPIClass;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Random;

/**
 * @author SUNNY
 * @description: 从列表中随机获取一个
 * @create: 2023-03-03 18:06
 */
@Slf4j
@SPIClass
public class RandomDisuseStrategy implements DisuseStrategy {
    @Override
    public ConnectionInfo selectConnection(List<ConnectionInfo> connectionInfoList) {
        log.info("execute random disuse strategy...");
        if (connectionInfoList.isEmpty()) {
            return null;
        }
        return connectionInfoList.get(new Random().nextInt(connectionInfoList.size()));
    }
}
