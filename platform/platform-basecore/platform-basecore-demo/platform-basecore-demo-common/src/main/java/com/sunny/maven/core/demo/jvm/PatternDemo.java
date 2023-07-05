package com.sunny.maven.core.demo.jvm;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author SUNNY
 * @description:
 * @create: 2023/7/3 17:31
 */
public class PatternDemo {
    private static final Pattern VERSION_PREFIX_PATTERN = Pattern.compile(".*/v(\\d+)/.*");
    public static void main(String[] args) {
        String url = "/user/v1/query";
        Matcher matcher = VERSION_PREFIX_PATTERN.matcher(url);
        if (matcher.matches()) {
            String versionNumber = matcher.group(1);
            System.out.println("提取到的版本号为: " + versionNumber);
        } else {
            System.out.println("未找到版本号");
        }
    }
}
