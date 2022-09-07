package com.sunny.maven.core.utils.delaytask;

import java.text.DateFormat;
import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author SUNNY
 * @description: JDK延迟队列元素
 * @create: 2022-08-04 14:49
 */
public class DelayElement implements Delayed {
    /**
     * 延迟截止时间（单面：毫秒）
     */
    long delayTime = System.currentTimeMillis();

    /**
     * 获取剩余时间
     * @param timeUnit
     * @return
     */
    @Override
    public long getDelay(TimeUnit timeUnit) {
        return timeUnit.convert(delayTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    /**
     * 队列里元素的排序依据
     * @param delayed
     * @return
     */
    @Override
    public int compareTo(Delayed delayed) {
        if (this.getDelay(TimeUnit.MILLISECONDS) > delayed.getDelay(TimeUnit.MILLISECONDS)) {
            return 1;
        } else if (this.getDelay(TimeUnit.MILLISECONDS) < delayed.getDelay(TimeUnit.MILLISECONDS)) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return DateFormat.getDateTimeInstance().format(new Date(delayTime));
    }

    /**
     * 构造函数
     * @param dateTime
     */
    public DelayElement(long dateTime) {
        this.delayTime = this.delayTime + dateTime;
    }
}
