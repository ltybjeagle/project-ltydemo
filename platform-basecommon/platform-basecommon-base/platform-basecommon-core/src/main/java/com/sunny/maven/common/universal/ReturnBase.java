package com.sunny.maven.common.universal;

import com.sunny.maven.common.constant.CommonConstant;
import com.sunny.maven.enums.ReturnEnum;
import com.sunny.maven.holder.RequestHolderUtil;
import org.slf4j.MDC;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author SUNNY
 * @description: 返回对象
 * @create: 2021-11-17 13:38
 */
public class ReturnBase implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 日志跟踪标识
     */
    public static final String TRACE_ID = "TRACE_ID";

    /**
     * 返回响应对象
     * @param returnEnum 枚举编码
     * @return ReturnBase
     */
    public static ReturnBase error(ReturnEnum returnEnum) {
        return error(returnEnum, RequestHolderUtil.getLang());
    }

    /**
     * 返回响应对象
     * @param returnEnum 枚举编码
     * @param language 类型
     * @return ReturnBase
     */
    public static ReturnBase error(ReturnEnum returnEnum, String language) {
        String msg;
        if (Objects.isNull(language)) {
            return new ReturnBase(MDC.get(TRACE_ID), returnEnum.getMsgCode(), returnEnum.getMsgEn(),
                    null, 0);
        }
        switch(language){
            case CommonConstant.SIMPLE_CHINESE:
                msg = returnEnum.getMsgCn();
                break;
            default:
                msg = returnEnum.getMsgEn();
                break;
        }
        return new ReturnBase(MDC.get(TRACE_ID), returnEnum.getMsgCode(), msg, null, 0);
    }

    /**
     * 私有构造函数
     * @param traceId 跟踪标识
     * @param msgCode 编码
     * @param msg 消息
     * @param data 数据
     * @param total 总数
     */
    private ReturnBase(String traceId, String msgCode, String msg, Object data, Integer total) {
        this.traceId = traceId;
        this.msgCode = msgCode;
        this.msg = msg;
        this.data = data;
        this.total = total;
    }

    /**
     * 跟踪标识
     */
    private String traceId;
    /**
     * 编码
     */
    private String msgCode;
    /**
     * 消息
     */
    private String msg;
    /**
     * 数据
     */
    private Object data;
    /**
     * 总数
     */
    private Integer total;

    /**
     * 跟踪标识
     * @return String
     */
    public String getTraceId() {
        return traceId;
    }
    /**
     * 编码
     * @return String
     */
    public String getMsgCode() {
        return msgCode;
    }
    /**
     * 消息
     * @return String
     */
    public String getMsg() {
        return msg;
    }
    /**
     * 数据
     * @return Object
     */
    public Object getData() {
        return data;
    }
    /**
     * 总数
     * @return Integer
     */
    public Integer getTotal() {
        return total;
    }
}
