package com.sunny.maven.observabletest;

import io.reactivex.Observable;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/12/17 0:37
 * @description：定时调度
 * @modified By：
 * @version: 1.0.0
 */
public class ObservableTimerTest implements ObservableLog {
    /**
     * 延迟任务
     */
    @Test
    public void timer_test() {
        Observable.timer(2, TimeUnit.SECONDS).subscribe(x -> log(10));
        sleep(10, TimeUnit.SECONDS);
    }

    /**
     * 定时任务
     */
    @Test
    public void interval_test() {
        Observable.interval(2, TimeUnit.SECONDS).subscribe(x -> log(10));
        sleep(10, TimeUnit.SECONDS);
    }

    private static void sleep(int tomeout, TimeUnit unit) {
        try {
            unit.sleep(tomeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
