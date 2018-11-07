package com.liuty.maven.lambda;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
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
        forEach(Arrays.asList(1,2,3,4,5), (Integer i) -> System.out.println(i));
        List<Integer> resultI = map(Arrays.asList("test1", "test2", "test3"), (String s) -> s.length());
        // 方法引用
        List<String> testList = Arrays.asList("a", "b", "A", "B");
        testList.sort((String s1, String s2) -> s1.compareToIgnoreCase(s2));  // 函数式
        testList.sort((s1, s2) -> s1.compareToIgnoreCase(s2));  // 函数式   类型推断
        testList.sort(String :: compareToIgnoreCase);  // 方法引用
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

    /**
     * JDK封装函数式接口Consumer   函数签名  void accept(T)
     * @param list
     * @param consumer
     * @param <T>
     */
    public static <T> void  forEach(List<T> list, Consumer<T> consumer) {
        for (T t : list) {
            consumer.accept(t);
        }
    }

    /**
     * JDK封装函数式接口Function   函数签名  R apply(L)
     * @param list
     * @param function
     * @param <L>
     * @param <R>
     * @return
     */
    public static <L, R> List<R> map(List<L> list, Function<L, R> function) {
        List<R> result = new ArrayList<>();
        for (L l : list) {
            result.add(function.apply(l));
        }
        return result;
    }
}
