package com.liuty.maven.logutil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.function.Supplier;
import static java.util.stream.Collectors.*;

import java.util.stream.Stream;

/**
 * logger Tools
 */
public class LoggerUtil {

    private static Logger logger = LoggerFactory.getLogger(LoggerUtil.class);

    /**
     * 日志输出
     * @param level 日志级别
     * @param msgSupplier 输出信息
     * @param obj 输出变量
     */
    public static void logger(LoggerLevel level, Supplier<String> msgSupplier, Object ...obj) {
        Stream<String> classNames = Arrays.stream(Thread.currentThread().getStackTrace()).limit(3)
                .map(StackTraceElement::getClassName);
        String className = classNames.collect(toList()).get(2);
        String msg = "[{}] @@@ " + msgSupplier.get();
        switch (level) {
            case INFO:
                if (logger.isInfoEnabled()) {
                    logger.info(msg, className, obj);
                }
                break;
            case ERROR:
                if (logger.isErrorEnabled()) {
                    logger.error(msg, className, obj);
                }
                break;
            case DEBUG:
                if (logger.isDebugEnabled()) {
                    logger.debug(msg, className, obj);
                }
                break;
                default: break;
        }
    }
}
