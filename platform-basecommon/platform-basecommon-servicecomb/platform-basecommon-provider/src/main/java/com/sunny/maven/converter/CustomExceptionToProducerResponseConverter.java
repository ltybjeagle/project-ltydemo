package com.sunny.maven.converter;

import com.sunny.maven.common.RespErrorMsg;
import com.sunny.maven.constant.ErrorEnum;
import com.sunny.maven.exception.AppException;
import com.sunny.maven.util.json.JsonUtils;
import org.apache.servicecomb.swagger.invocation.Response;
import org.apache.servicecomb.swagger.invocation.SwaggerInvocation;
import org.apache.servicecomb.swagger.invocation.exception.ExceptionToProducerResponseConverter;
import org.apache.servicecomb.swagger.invocation.exception.InvocationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author SUNNY
 * @description: 异常处理拦截器
 * @create: 2021-08-21 16:50
 */
public class CustomExceptionToProducerResponseConverter implements ExceptionToProducerResponseConverter<Throwable> {
    /**
     * 默认异常信息
     */
    private static final ErrorEnum DEFAULT_ENUM = ErrorEnum.OPERATION_EXCEPTION;
    /**
     * 异常堆栈截取
     */
    private static final int COUNT = 5;
    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(CustomExceptionToProducerResponseConverter.class);
    /**
     * 实现类所处理的异常类型。如果该方法返回null，则说明此实现类为默认converter
     * @return clazz
     */
    @Override
    public Class<Throwable> getExceptionClass() {
        return Throwable.class;
    }

    /**
     * 实现类的优先级，该方法返回的值越小，优先级越高，如果不覆写该方法的话，则返回默认优先级0。
     * 对于处理同一异常类型的converter（或默认converter），只有优先级最高的生效。
     * @return int
     */
    @Override
    public int getOrder() {
        return 0;
    }

    /**
     * 异常处理
     * @param swaggerInvocation 接口
     * @param e 异常
     * @return Response
     */
    @Override
    public Response convert(SwaggerInvocation swaggerInvocation, Throwable e) {
        logger.error("异常信息：{}", e.getMessage());
        if (e instanceof AppException) {
            return rtnResponse(buildRespErrorMsg((AppException) e));
        }
        if (e instanceof SQLException) {
            return rtnResponse(buildRespErrorMsg((SQLException) e));
        }
        if (e instanceof ArithmeticException) {
            return rtnResponse(buildRespErrorMsg(e));
        }
        if (e instanceof NullPointerException) {
            return rtnResponse(buildRespErrorMsg(e));
        }
        if (e instanceof RuntimeException) {
            return rtnResponse(buildRespErrorMsg(e));
        }
        if (e instanceof Exception) {
            return rtnResponse(buildRespErrorMsg(e));
        }
        return rtnResponse(new RespErrorMsg.RespErrorMsgBuilder().build());
    }

    /**
     * 构建异常对象（AppException）
     * @param appException 自定义异常类
     * @return RespErrorMsg
     */
    private RespErrorMsg buildRespErrorMsg(AppException appException) {
        String code = Optional.ofNullable(appException.getCode()).orElse(DEFAULT_ENUM.getCode());
        return new RespErrorMsg.RespErrorMsgBuilder().code(code).
                message(Optional.ofNullable(appException.getMessage()).
                        orElse(ErrorEnum.getErrorEnum(code).getMessage())).
                advise(Optional.ofNullable(appException.getAdvise()).orElse(ErrorEnum.getErrorEnum(code).getAdvise())).
                stackTraces(Arrays.toString(Stream.of(appException.getStackTrace()).limit(COUNT).toArray())).build();
    }
    /**
     * 构建异常对象（SQLException）
     * @param sqlException 异常类
     * @return RespErrorMsg
     */
    private RespErrorMsg buildRespErrorMsg(SQLException sqlException) {
        String code = ErrorEnum.DATABASE_EXCEPTION.getCode();
        sqlException.getErrorCode();
        return new RespErrorMsg.RespErrorMsgBuilder().
                message(Optional.ofNullable(sqlException.getErrorCode() + "-" + sqlException.getMessage()).
                        orElse(ErrorEnum.getErrorEnum(code).getMessage())).
                stackTraces(Arrays.toString(Stream.of(sqlException.getStackTrace()).limit(COUNT).toArray())).
                build();
    }
    /**
     * 构建异常对象（Exception、RuntimeException、NullPointerException、ArithmeticException）
     * @param exception 异常类
     * @return RespErrorMsg
     */
    private RespErrorMsg buildRespErrorMsg(Throwable exception) {
        String code = DEFAULT_ENUM.getCode();
        return new RespErrorMsg.RespErrorMsgBuilder().
                message(Optional.ofNullable(exception.getMessage()).orElse(ErrorEnum.getErrorEnum(code).getMessage())).
                stackTraces(Arrays.toString(Stream.of(exception.getStackTrace()).limit(COUNT).toArray())).
                build();
    }
    /**
     * 响应对象
     * @param respMsg 异常封装对象
     * @return Response
     */
    private Response rtnResponse(RespErrorMsg respMsg) {
        InvocationException state =
                new InvocationException(javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR,
                        JsonUtils.toJson(respMsg));
        return Response.failResp(state);
    }
}
