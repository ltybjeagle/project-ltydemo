package com.liuty.maven.lambda;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * 函数式接口定义
 */
@FunctionalInterface
public interface BufferedReaderProcessor {
    String process(BufferedReader br) throws IOException;
}
