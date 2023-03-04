package com.sunny.maven.rpc.disuse.lfu;

import com.sunny.maven.rpc.disuse.api.DisuseStrategy;
import com.sunny.maven.rpc.disuse.api.connection.ConnectionInfo;
import com.sunny.maven.rpc.spi.annotation.SPIClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author SUNNY
 * @description: 在一段时间内，数据被使用次数最少的，优先被淘汰。
 * @create: 2023-03-03 14:57
 */
@Slf4j
@SPIClass
public class LfuDisuseStrategy implements DisuseStrategy {

    private final Comparator<ConnectionInfo> useCountComparator =
            (o1, o2) -> o1.getUseCount() - o2.getUseCount() > 0 ? 1 : -1;
    @Override
    public ConnectionInfo selectConnection(List<ConnectionInfo> connectionInfoList) {
        log.info("execute lfu disuse strategy...");
        if (connectionInfoList.isEmpty()) {
            return null;
        }
        Collections.sort(connectionInfoList, useCountComparator);
        return connectionInfoList.get(0);
    }
}
