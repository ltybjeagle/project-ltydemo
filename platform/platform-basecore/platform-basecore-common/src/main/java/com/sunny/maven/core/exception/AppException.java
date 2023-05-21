package com.sunny.maven.core.exception;

import com.sunny.maven.core.common.resp.rstenum.ResultCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author SUNNY
 * @description: 自定义异常
 * @create: 2022-07-25 15:19
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AppException extends RuntimeException {
    private static final long serialVersionUID = -8711500089130961919L;
    /**
     * 异常编码
     */
    private Integer code;
    public AppException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public AppException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getCodeMsg());
        this.code = resultCodeEnum.getCode();
    }

    @Override
    public String toString() {
        return "AppException{" + "code=" + code + ", message=" + this.getMessage() + '}';
    }
}
