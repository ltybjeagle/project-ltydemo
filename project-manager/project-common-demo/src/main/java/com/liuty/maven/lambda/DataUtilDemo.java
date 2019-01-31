package com.liuty.maven.lambda;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

/**
 * JDK8.0 日期时间类
 */
public class DataUtilDemo {

    public static void main(String ...args) {
        LocalDate ld = LocalDate.of(2018, 11, 22);
        System.out.println("LocalDate.toString:" + ld.toString());
        System.out.println("LocalDate.withYear:" + ld.withYear(2011));
        System.out.println("LocalDate.withDayOfMonth:" + ld.withDayOfMonth(25));
        System.out.println("LocalDate.with:" + ld.with(ChronoField.MONTH_OF_YEAR, 8));
        System.out.println("#############################################################");
        System.out.println("LocalDate.plusWeeks:" + ld.plusWeeks(1));
        System.out.println("LocalDate.minusWeeks:" + ld.minusYears(3));
        System.out.println("LocalDate.plus:" + ld.plus(6, ChronoUnit.MONTHS));
        System.out.println("#############################################################");
        System.out.println("TemporalAdjusters.nextOrSame:" + ld.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)));
        System.out.println("TemporalAdjusters.lastDayOfMonth:" + ld.with(TemporalAdjusters.lastDayOfMonth()));
        System.out.println("#############################################################");
        LocalDate nowData = LocalDate.now();
        System.out.println("LocalDate:" + nowData.get(ChronoField.YEAR));
        System.out.println("LocalDate:" + nowData.get(ChronoField.MONTH_OF_YEAR));
        System.out.println("LocalDate:" + nowData.get(ChronoField.DAY_OF_MONTH));
        System.out.println("#############################################################");
        LocalTime lt = LocalTime.now();
        System.out.println("LocalTime.getHour:" + lt.getHour());
        System.out.println("LocalTime.getMinute:" + lt.getMinute());
        System.out.println("LocalTime.getSecond:" + lt.getSecond());
        System.out.println("LocalDate.parse:" + LocalDate.parse("2018-11-22"));
        System.out.println("#############################################################");
        LocalDateTime ldt = LocalDateTime.now();
        LocalDate ld1 = ldt.toLocalDate();
        LocalTime lt1 = ldt.toLocalTime();
        System.out.println("Instant.now:" + Instant.now());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }
}
