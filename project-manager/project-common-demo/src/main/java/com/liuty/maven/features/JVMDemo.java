package com.liuty.maven.features;

/**
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
