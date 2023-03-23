package com.sunny.maven.microservice.utils.id;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author SUNNY
 * @description: 雪花算法工厂
 * @create: 2023-03-22 12:12
 */
public class SnowFlakeFactory {

    /**
     * 默认数据中心id
     */
    private static final long DEFAULT_DATA_CENTER_ID = 1;
    /**
     * 默认的机器id
     */
    private static final long DEFAULT_MACHINE_ID = 1;

    /**
     * 默认的雪花算法句柄
     */
    private static final String DEFAULT_SNOW_FLAKE = "snow_flake";

    /**
     * 缓存SnowFlake对象
     */
    private static ConcurrentMap<String, SnowFlake> snowFlakeCache = new ConcurrentHashMap<>(2);

    public static SnowFlake getSnowFlake(long dataCenterId, long machineId) {
        return new SnowFlake(dataCenterId, machineId);
    }

    public static SnowFlake getSnowFlake() {
        return new SnowFlake(DEFAULT_DATA_CENTER_ID, DEFAULT_MACHINE_ID);
    }

    public static SnowFlake getSnowFlakeFromCache() {
        SnowFlake snowFlake = snowFlakeCache.get(DEFAULT_SNOW_FLAKE);
        if (snowFlake == null) {
            snowFlake = new SnowFlake(DEFAULT_DATA_CENTER_ID, DEFAULT_MACHINE_ID);
            snowFlakeCache.put(DEFAULT_SNOW_FLAKE, snowFlake);
        }
        return snowFlake;
    }

    /**
     * 根据数据中心id和机器id从缓存中获取全局id
     * @param dataCenterId: 取值为1~31
     * @param machineId: 取值为1~31
     */
    public static SnowFlake getSnowFlakeByDataCenterIdAndMachineIdFromCache(Long dataCenterId, Long machineId) {
        if (dataCenterId > SnowFlake.getMaxDataCenterNum() || dataCenterId < 0) {
            throw new IllegalArgumentException("dataCenterId can't be greater than MAX_DATA_CENTER_NUM or less than 0");
        }
        if (machineId > SnowFlake.getMaxMachineNum() || machineId < 0) {
            throw new IllegalArgumentException("machineId can't be greater than MAX_MACHINE_NUM or less than 0");
        }
        String key = DEFAULT_SNOW_FLAKE.concat("_").
                concat(String.valueOf(dataCenterId)).concat("_").
                concat(String.valueOf(machineId));
        SnowFlake snowFlake = snowFlakeCache.get(key);
        if (snowFlake == null) {
            snowFlake = new SnowFlake(dataCenterId, machineId);
            snowFlakeCache.put(key, snowFlake);
        }
        return snowFlake;
    }
}
