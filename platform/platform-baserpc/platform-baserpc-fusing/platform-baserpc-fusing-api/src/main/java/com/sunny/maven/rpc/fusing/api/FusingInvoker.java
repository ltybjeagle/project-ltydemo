package com.sunny.maven.rpc.fusing.api;

import com.sunny.maven.rpc.constants.RpcConstants;
import com.sunny.maven.rpc.spi.annotation.SPI;

/**
 * @author SUNNY
 * @description: 熔断SPI接口
 * @create: 2023-03-11 16:42
 */
@SPI(value = RpcConstants.DEFAULT_FUSING_INVOKER)
public interface FusingInvoker {

    /**
     * 是否会触发熔断操作，规则如下：
     * 1.断路器默认处于“关闭”状态，当错误个数或错误率到达阈值，就会触发断路器“开启”。
     * 2.断路器开启后进入熔断时间，到达熔断时间终点后重置熔断时间，进入“半开启”状态。
     * 3.在半开启状态下，如果服务能力恢复，则断路器关闭熔断状态。进而进入正常的服务状态。
     * 4.在半开启状态下，如果服务能力未能恢复，则断路器再次触发服务熔断，进入熔断时间。
     * @return 是否要触发熔断，true：触发熔断，false：不触发熔断
     */
    boolean invokeFusingStrategy();

    /**
     * 处理请求的次数
     */
    void incrementCount();

    /**
     * 访问成功
     */
    void markSuccess();

    /**
     * 访问失败
     */
    void markFail();

    /**
     * 在milliSeconds毫秒内错误数量或者错误百分比达到totalFailure，则触发熔断操作
     * @param totalFailure 在milliSeconds毫秒内触发熔断操作的上限值
     * @param milliSeconds 毫秒数
     */
    default void init(double totalFailure, int milliSeconds) {}
}
