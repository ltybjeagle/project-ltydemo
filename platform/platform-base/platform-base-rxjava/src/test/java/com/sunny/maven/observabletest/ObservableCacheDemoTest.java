package com.sunny.maven.observabletest;

import io.reactivex.Observable;
import org.junit.Test;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/12/13 21:05
 * @description：缓存事件源
 * @modified By：
 * @version: 1.0.0
 */
public class ObservableCacheDemoTest {

    /**
     * 缓存事件源
     */
    @Test
    public void hot_Observable() {
        Observable<Object> observable = Observable.create(observer -> {
            observer.onNext("处理的数字是：" + Math.random() * 100);
            observer.onComplete();
        }).cache();
        observable.subscribe(consumer -> {
            System.out.println("我处理的元素是：" + consumer);
        });
        observable.subscribe(consumer -> {
            System.out.println("我处理的元素是：" + consumer);
        });
    }
}
