package com.sunny.maven.rpc.disuse.fifo;

import com.sunny.maven.rpc.disuse.api.DisuseStrategy;
import com.sunny.maven.rpc.disuse.api.connection.ConnectionInfo;
import com.sunny.maven.rpc.spi.annotation.SPIClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author SUNNY
 * @description: 判断被存储的时间，离目前最远的数据优先被淘汰。
 * @create: 2023-03-03 14:26
 */
@Slf4j
@SPIClass
public class FifoDisuseStrategy implements DisuseStrategy {
    private final Comparator<ConnectionInfo> connectionTimeComparator =
            (o1, o2) -> o1.getConnectionTime() - o2.getConnectionTime() > 0 ? 1 : -1;
    @Override
    public ConnectionInfo selectConnection(List<ConnectionInfo> connectionInfoList) {
        log.info("execute fifo disuse strategy...");
        if (connectionInfoList.isEmpty()) {
            return null;
        }
        Collections.sort(connectionInfoList, connectionTimeComparator);
        return connectionInfoList.get(0);
    }
}
