package com.liuty.maven.feature;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: java7新特性
 * @author: Sunny
 * @date: 2019/10/10
 */
public class Java7Feature {
    public static void main(String ...args) {
        switchFeature("2");
        integerFeature();
        exceptionFeature();
        // 砖石操作符
        List<String> list = new ArrayList<>();
        // 函数支持泛型可变参数
    }
    /**
     * switch支持字符串类型
     * @param str
     */
    public static void switchFeature(String str) {
        switch (str) {
            case "1" :
                System.out.println("1");
                break;
            case "2" :
                System.out.println("2");
                break;
            case "3" :
                System.out.println("3");
                break;
            default:
                System.out.println("0");
        }
    }
    /**
     * 数字文本表示法
     */
    public static void integerFeature() {
        int x = 0b1100100;
        int z = 0b1100_1000;
        long y = 2_147_483_648L;
    }
    /**
     * 异常处理优化
     */
    public static void exceptionFeature() {
        File file = null;
        try {
            file = new File("");
            InputStreamReader in = new FileReader(file);
        } catch (NullPointerException|FileNotFoundException e) {
        } catch (IOException iox) {
        } catch (final Exception ex) {
            // 异常重抛，抛出具体异常信息
            throw ex;
        }

        // 编辑器实现资源关闭close方法
        try (OutputStreamWriter out = new FileWriter(file)) {

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
