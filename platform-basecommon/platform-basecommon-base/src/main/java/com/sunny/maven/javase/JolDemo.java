package com.sunny.maven.javase;

import org.openjdk.jol.vm.VM;

/**
 * @author SUNNY
 * @description: JOL（Java Object Layout）类库
 * @create: 2021-07-31 15:55
 */
public class JolDemo {
    public static void main(String ...args) {
        // 获取对象内存地址
        String str = "test";
        System.out.println("the memory address is : " + VM.current().addressOf(str));
    }
}
