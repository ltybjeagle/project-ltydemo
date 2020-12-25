package com.sunny.maven.observabletest;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/12/23 22:12
 * @description：无限流事件源
 * @modified By：
 * @version: 1.0.0
 */
public class ObservableStreamDemoTest implements ObservableLog {
    /**
     * 主线程——无限流
     */
    @Test
    public void infinite_test() {
        Observable<Object> observable = Observable.create(observer -> {
            BigInteger i = BigInteger.ZERO;
            while (true) {
                observer.onNext(i);
                i = i.add(BigInteger.ONE);
            }
        });
        observable.subscribe(x -> log(x));
    }

    /**
     * 基于子线程实现
     */
    @Test
    public void infinite_thread_test() {
        Observable<Object> observable = Observable.create(observer -> {
            Runnable runnable = () -> {
                BigInteger i = BigInteger.ZERO;
                while (true) {
                    observer.onNext(i);
                    i = i.add(BigInteger.ONE);
                    System.out.println(Thread.currentThread().getName() + " 下一个消费的数字" + i.toString());
                }
            };
            new Thread(runnable).start();
        });
        observable.subscribe(x -> log(x));
        observable.subscribe(x -> log(x));
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 基于子线程实现，可以取消订阅
     */
    @Test
    public void infinite_unsubscribed_thread_test() {
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
        });
        final Disposable subscribe1 = observable.subscribe(x -> log(x));
        final Disposable subscribe2 = observable.subscribe(x -> log(x));
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
     * 基于线程池实现
     */
    @Test
    public void pool_pushCollection() {
        List<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(2);
        integers.add(3);
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        try {
            pushCollection(integers, forkJoinPool).subscribe(x -> log(x));
            pushCollection(integers, forkJoinPool).subscribe(x -> log(x));
        } finally {
            try {
                forkJoinPool.shutdown();
                int shutdownDelaySec = 1;
                System.out.println(".......等待 " + shutdownDelaySec + " 秒后结束服务......");
                forkJoinPool.awaitTermination(shutdownDelaySec, TimeUnit.SECONDS);
            } catch (Exception ex) {
                System.out.println("捕获到 forkJoinPool.awaitTermination() 方法： " + ex.getClass().getName());
            } finally {
                System.out.println("调用 forkJoinPool.shutdownNow() 结束服务...");
                List<Runnable> l = forkJoinPool.shutdownNow();
                System.out.println("还剩 " + l.size() + " 个任务等待执行，服务已关闭");
            }
        }
    }

    public static Observable<Integer> pushCollection(List<Integer> ids, ForkJoinPool commonPool) {
        return Observable.create(observer -> {
            AtomicInteger atomicInteger = new AtomicInteger(ids.size());
            commonPool.submit(() -> {
                ids.forEach(id -> {
                    observer.onNext(id);
                    if (atomicInteger.decrementAndGet() == 0) {
                        observer.onComplete();
                    }
                });
            });
        });
    }

    /**
     * 基于线程池实现(订阅者业务按多线程执行)
     */
    @Test
    public void pool_pushCollectionDanger() {
        List<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(2);
        integers.add(3);
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        pushCollection(integers, forkJoinPool).subscribe(x -> log(x + "我是订阅者1"));
        pushCollection(integers, forkJoinPool).subscribe(x -> log(x + "我是订阅者2"));
        sleep(2, TimeUnit.SECONDS);
    }

    public static Observable<Integer> pushCollectionDanger(List<Integer> ids, ForkJoinPool commonPool) {
        return Observable.create(observer -> {
            AtomicInteger atomicInteger = new AtomicInteger(ids.size());
            ids.forEach(id -> commonPool.submit(() -> {
                observer.onNext(id);
                if (atomicInteger.decrementAndGet() == 0) {
                    commonPool.shutdownNow();
                    observer.onComplete();
                }
            }));
        });
    }

    private static void sleep(int tomeout, TimeUnit unit) {
        try {
            unit.sleep(tomeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
