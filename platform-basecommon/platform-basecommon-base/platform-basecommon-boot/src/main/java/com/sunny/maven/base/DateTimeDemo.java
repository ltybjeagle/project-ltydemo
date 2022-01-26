package com.sunny.maven.base;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;

/**
 * @author SUNNY
 * @description: 时间工具集
 * @create: 2021-07-24 13:49
 */
public class DateTimeDemo {

    public static void main(String ...args) {
        DateTimeDemo dateTimeDemo = new DateTimeDemo();
        dateTimeDemo.commonDate();
    }

    /**
     * Apache Common Date 工具
     */
    public void commonDate() {
        /*
         * 日期、时间格式化
         */
        System.out.println(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        try {
            System.out.println(DateUtils.parseDate("2021-05-01 01:01:01", "yyyy-MM-dd HH:mm:ss"));
            System.out.println(DateUtils.addHours(new Date(), 1));
        } catch (Exception e) {}
    }
}
