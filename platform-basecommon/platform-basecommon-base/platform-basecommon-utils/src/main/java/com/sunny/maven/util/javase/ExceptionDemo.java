package com.sunny.maven.util.javase;

/**
 * @author SUNNY
 * @description: 异常处理类
 * @create: 2021-07-31 15:59
 */
public class ExceptionDemo {
    public static void main(String ...args) throws Exception {
        while (true) {
            throw new MyException();
        }
    }

    static class MyException extends Exception {
        @Override
        public synchronized Throwable fillInStackTrace() {
            return this;
        }
    }
}
