package com.sunny.maven.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @author SUNNY
 * @description: 文件工具集
 * @create: 2021-07-24 13:50
 */
public class FileDemo {
    public static void main(String ...args) {
        FileDemo fileDemo = new FileDemo();
        fileDemo.commonFile();
    }

    /**
     * Apache Common File 工具
     */
    public void commonFile() {
        try {
            // 读取文件
            File file = new File("E:\\test.txt");
            List<String> lines = FileUtils.readLines(file, Charset.defaultCharset());
            System.out.println(lines);
            // 写入文件
            FileUtils.writeLines(new File("E:\\test1.txt"), lines);
            // 复制文件
            FileUtils.copyFile(new File("E:\\src.txt"), new File("E:\\dest.txt"));
        } catch (Exception e) {
        }
    }
}
