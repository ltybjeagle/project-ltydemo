package com.sunny.maven.observabletest;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import org.junit.Test;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/12/23 22:11
 * @description：diaposable
 * @modified By：
 * @version: 1.0.0
 */
public class ObservableDisposableTest implements ObservableLog {
    /**
     * 基于子线程实现，可以取消订阅,使用缓存
     */
    @Test
    public void infinite_unsubscribed_cache_thread_test() {
        Observable<Object> observable = Observable.create(observer -> {
            Runnable runnable = () -> {
                BigInteger i = BigInteger.ZERO;
                // 增加终止条件
                while (!observer.isDisposed()) {
                    observer.onNext(i);
                    i = i.add(BigInteger.ONE);
                    System.out.println(Thread.currentThread().getName() + " 下一个消费的数字" + i.toString());
                }
            };
            new Thread(runnable).start();
        }).cache();
        final Disposable subscribe1 = observable.subscribe(x -> log("一郎神" + x));
        final Disposable subscribe2 = observable.subscribe(x -> log("二郎神" + x));
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        subscribe1.dispose();
        subscribe2.dispose();
        System.out.println("我取消了订阅");
        try {
            TimeUnit.MILLISECONDS.sleep(500);
            System.out.println("程序结束");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 基于子线程实现，可以取消订阅,使用缓存
     */
    @Test
    public void hot_s_Observable() {
        Observable<Object> observable = Observable.create(observer -> {
            Runnable runnable = () -> {
                BigInteger i = BigInteger.ZERO;
                while (true) {
                    observer.onNext(i);
                    i = i.add(BigInteger.ONE);
                    if (i.compareTo(BigInteger.valueOf(50)) == 0) {
                        observer.onComplete();
                        break;
                    }
                    System.out.println(Thread.currentThread().getName() + " 下一个消费的数字" + i.toString());
                }
            };
            new Thread(runnable).start();
        }).cache();
        observable.subscribe(x -> log("一郎神" + x));
        observable.subscribe(x -> log("二郎神" + x));
        try {
            TimeUnit.MILLISECONDS.sleep(50);
            System.out.println("程序进行50ms");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            TimeUnit.MILLISECONDS.sleep(500);
            System.out.println("程序结束");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
