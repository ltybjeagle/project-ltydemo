package com.liuty.maven.lambda;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * 函数式用例，行为参数化
 */
public class LambdaDemo {

    public static void main(String ...args) throws Exception {
        // 读取一行数据
        String oneLine = processFile((BufferedReader br) -> br.readLine());
        // 读取两行数据
        String twoLines = processFile((BufferedReader br) -> br.readLine() + br.readLine());
        List<String> listOfString = new ArrayList<>();
        Predicate<String> nonEmptyStringPredicate = (String s) -> !s.isEmpty();
        List<String> nonList = filter(listOfString, nonEmptyStringPredicate);
    }

    /**
     * 行为代码传入方法
     * @param processor 函数式接口
     * @return
     * @throws IOException
     */
    public static String processFile(BufferedReaderProcessor processor) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
            return processor.process(br);
        }
    }

    /**
     * JDK封装函数式接口Predicate   函数签名  boolean test(T)
     * @param list
     * @param predicate
     * @param <T>
     * @return
     */
    public static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        for (T t : list) {
            if (predicate.test(t)) {
                result.add(t);
            }
        }
        return result;
    }
}
