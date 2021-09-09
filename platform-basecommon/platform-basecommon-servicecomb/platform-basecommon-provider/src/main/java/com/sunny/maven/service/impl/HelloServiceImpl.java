package com.sunny.maven.service.impl;

import com.sunny.maven.constant.ErrorEnum;
import com.sunny.maven.exception.AppException;
import com.sunny.maven.service.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

/**
 * @author SUNNY
 * @description: 逻辑实现类
 * @create: 2021-08-31 17:16
 */
@Service
public class HelloServiceImpl implements HelloService {
    private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);

    /**
     * 请求接口
     * @param name 参数
     * @return 响应
     */
    @Override
    public String sayHello(String name) throws AppException {
        logger.info("进入service操作");
        if ("op".equalsIgnoreCase(name)) {
            throw new AppException.AppExceptionBuilder().
                    code(ErrorEnum.OPERATION_EXCEPTION.getCode()).build();
        }
        if ("null".equalsIgnoreCase(name)) {
            throw new NullPointerException("空指针");
        }
        if ("db".equalsIgnoreCase(name)) {
            try {
                throw new SQLException("bad sql execute");
            } catch (Exception ignored) {
                throw new AppException.AppExceptionBuilder().
                        code(ErrorEnum.DATABASE_EXCEPTION.getCode()).build();
            }
        }

        return "Hello " + name;
    }
}
