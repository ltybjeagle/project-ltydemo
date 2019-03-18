package com.liuty.maven.features;

/**
 * HotSpot VM是32位JVM，内存地址空间限制在4G
 * 32位系统，JAVA堆的大小受限于操作系统：
 *      1、Windows操作系统，JAVA堆大小1.5G
 *      2、Linux操作系统，JAVA堆大小2.5G到3.0G
 * 64位HotSpot VM增大了JAVA堆，并使用压缩指针的方式提供了32位JVM的性能。
 *      1、内存地址：16进制数表示
 *      2、包含三个组件：VM运行时、JIT编译器、内存管理。
 *
 * 常量池：
 *      1、CLASS常量池：字节码文件包含类的常量池（字面量、符号引用），供运行时使用
 *      2、字符串常量池
 *
 * JVM参数：
 *      -XX:+TraceClassLoading  输出类加载信息
 *      -XX:+HeapDumpOnOutOfMemoryError  堆内存溢出时导出堆信息（可以用MAT工具分析问题）
 *      -XX:HeapDumpPath  堆内存溢出时导出文件的路径
 */
public class JVMDemo {

    public static void main(String ...args) {
        System.out.println("JVM参数测试");
    }
}
