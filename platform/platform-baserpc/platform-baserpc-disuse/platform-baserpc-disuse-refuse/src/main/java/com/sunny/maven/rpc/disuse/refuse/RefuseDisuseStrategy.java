package com.sunny.maven.rpc.disuse.refuse;

import com.sunny.maven.rpc.common.exception.RefuseException;
import com.sunny.maven.rpc.disuse.api.DisuseStrategy;
import com.sunny.maven.rpc.disuse.api.connection.ConnectionInfo;
import com.sunny.maven.rpc.spi.annotation.SPIClass;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author SUNNY
 * @description: 拒绝新连接
 * @create: 2023-03-03 18:26
 */
@Slf4j
@SPIClass
public class RefuseDisuseStrategy implements DisuseStrategy {
    @Override
    public ConnectionInfo selectConnection(List<ConnectionInfo> connectionInfoList) {
        log.info("execute refuse disuse strategy...");
        throw new RefuseException("refuse new connection...");
    }
}
