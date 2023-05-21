package com.sunny.maven.core.exception.extenum;

/**
 * @author SUNNY
 * @description: ExceptionEnum
 * @create: 2023-04-20 22:55
 */
public interface ExceptionEnum {
    /**
     * 获取异常编码
     * @return
     */
    Integer getCode();

    /**
     * 获取异常描述
     * @return
     */
    String getCodeMsg();

}
