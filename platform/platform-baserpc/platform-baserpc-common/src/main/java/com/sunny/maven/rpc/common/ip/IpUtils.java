package com.sunny.maven.rpc.common.ip;

import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;

/**
 * @author SUNNY
 * @description: IP工具类
 * @create: 2023-02-07 15:06
 */
@Slf4j
public class IpUtils {
    public static InetAddress getLocalInetAddress() {
        try {
            return InetAddress.getLocalHost();
        } catch (Exception e) {
            log.error("get local ip address throws exception: {}", e.getMessage());
        }
        return null;
    }

    public static String getLocalAddress() {
        return getLocalInetAddress().toString();
    }

    public static String getLocalHostName() {
        return getLocalInetAddress().getHostName();
    }

    public static String getLocalHostIp() {
        return getLocalInetAddress().getHostAddress();
    }
}
