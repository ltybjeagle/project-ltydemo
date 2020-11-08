package com.sunny.maven.promisedemo;

import java.util.concurrent.CompletableFuture;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/9/5 20:50
 * @description：异步编程：Promise 模式
 * @modified By：
 * @version: 1.0.0
 */
public class PromiseDemo {
    public static void main(String ...args) throws Exception {
        String result = CompletableFuture.supplyAsync(() -> {
            return "hello";
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            return "world";
        }), (s1, s2) -> s1 + " " + s2).join();
        System.out.println(result);
    }
}
