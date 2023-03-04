package com.sunny.maven.rpc.disuse.lru;

import com.sunny.maven.rpc.disuse.api.DisuseStrategy;
import com.sunny.maven.rpc.disuse.api.connection.ConnectionInfo;
import com.sunny.maven.rpc.spi.annotation.SPIClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author SUNNY
 * @description: 判断最近被使用的时间，目前最远的数据优先被淘汰。
 * @create: 2023-03-03 17:43
 */
@Slf4j
@SPIClass
public class LruDisuseStrategy implements DisuseStrategy {
    private final Comparator<ConnectionInfo> lastUseTimeComparator =
            (o1, o2) -> o1.getLastUseTime() - o2.getLastUseTime() > 0 ? 1 : -1;
    @Override
    public ConnectionInfo selectConnection(List<ConnectionInfo> connectionInfoList) {
        log.info("execute lru disuse strategy...");
        if (connectionInfoList.isEmpty()) {
            return null;
        }
        Collections.sort(connectionInfoList, lastUseTimeComparator);
        return connectionInfoList.get(0);
    }
}
