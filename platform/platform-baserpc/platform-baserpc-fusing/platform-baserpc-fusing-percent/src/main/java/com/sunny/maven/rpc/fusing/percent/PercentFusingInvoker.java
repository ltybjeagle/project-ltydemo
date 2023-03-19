package com.sunny.maven.rpc.fusing.percent;

import com.sunny.maven.rpc.constants.RpcConstants;
import com.sunny.maven.rpc.fusing.base.AbstractFusingInvoker;
import com.sunny.maven.rpc.spi.annotation.SPIClass;
import lombok.extern.slf4j.Slf4j;

/**
 * @author SUNNY
 * @description: 在一段时间内基于错误率的熔断策略
 * @create: 2023-03-13 14:41
 */
@Slf4j
@SPIClass
public class PercentFusingInvoker extends AbstractFusingInvoker {
    @Override
    public boolean invokeFusingStrategy() {
        boolean result = false;
        switch (fusingStatus.get()) {
            // 关闭状态
            case RpcConstants.FUSING_STATUS_CLOSED:
                result = this.invokeClosedFusingStrategy();
                break;
            // 半开启状态
            case RpcConstants.FUSING_STATUS_HALF_OPEN:
                result = this.invokeHalfOpenFusingStrategy();
                break;
            // 开启状态
            case RpcConstants.FUSING_STATUS_OPEN:
                result = this.invokeOpenFusingStrategy();
                break;
            default:
                result = this.invokeClosedFusingStrategy();
                break;
        }
        log.info("execute percent fusing strategy, current fusing status is {}", fusingStatus.get());
        return result;
    }

    @Override
    public double getFailureStrategyValue() {
        if (currentCounter.get() <= 0) {
            return 0;
        }
        return currentFailureCounter.doubleValue() / currentCounter.doubleValue() * 100;
    }
}
