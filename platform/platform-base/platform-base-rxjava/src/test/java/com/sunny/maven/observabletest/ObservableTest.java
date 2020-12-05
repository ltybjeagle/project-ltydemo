package com.sunny.maven.observabletest;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.ObjectHelper;
import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/11/9 10:58
 * @description：Observable测试用例
 * @modified By：
 * @version: 1.0.0
 */
public class ObservableTest {
    /**
     * 定义数组事件源
     */
    @Test
    public void defer_test() {
        String[] monthArray = {"Jan", "Feb", "Mar", "Apl", "May", "Jun", "July",
                "Aug", "Sept", "Oct", "Nov", "Dec"};
        Observable.defer(() -> {
            return Observable.fromArray(monthArray);
        }).subscribe(System.out::println, Throwable::printStackTrace, () -> {
            System.out.println("Emission completed");
        });
    }

    /**
     * 定义空事件源
     */
    @Test
    public void empty_test() {
        Observable<Object> empty = Observable.empty();
        empty.subscribe(System.out::println, Throwable::printStackTrace, () -> {
            System.out.println("I am Done!! Completed normally");
        });
    }

    /**
     * 定义异常事件源
     */
    @Test
    public void error_test() {
        Observable<String> observable = Observable.error(new Exception("We got an Exception"));
        observable.subscribe(System.out::println, Throwable::printStackTrace, () -> {
            System.out.println("I am Done!! Completed normally");
        });
    }

    @Test
    public void rang_test() {
        log("Rang_test Before");
        Observable.range(5, 3).subscribe(ObservableTest::log);
        log("Rang_test After");
    }

    @Test
    public void just_test() {
        log("Just_test Before");
        Observable.just("Jan", "Feb", "Mar", "Apl", "May", "Jun").subscribe(ObservableTest::log);
        log("Just_test After");
    }

    /**
     * 自定义Observable.just()
     * @param item
     * @param <T>
     * @return
     */
    public static <T> Observable<T> just(T item) {
        ObjectHelper.requireNonNull(item, "The item is null");
        return Observable.create(subscriber -> {
            subscriber.onNext(item);
            subscriber.onComplete();
        });
    }

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

    /**
     * 基于多线程实现
     */
    @Test
    public void infinite_thread_test() {
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
        pushCollection(integers, forkJoinPool).subscribe(x -> log(x + "我是订阅者1"));
        pushCollection(integers, forkJoinPool).subscribe(x -> log(x + "我是订阅者2"));
        sleep(2, TimeUnit.SECONDS);
    }

    public static Observable<Integer> pushCollection(List<Integer> ids, ForkJoinPool commonPool) {
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

    /**
     * 异常处理
     */
    @Test
    public void observable_error_test_acc() {
        observable_error_test(1);
        observable_error_test(5);
    }

    /**
     * 使用封装API处理异常
     */
    @Test
    public void fromCallable_test() {
        error_test_pro(1).subscribe(x -> log(x), Throwable::printStackTrace,
                () -> System.out.println("Emission completed"));
        System.out.println("********************分割线**********************");
        error_test_pro(5).subscribe(x -> log(x), Throwable::printStackTrace,
                () -> System.out.println("Emission completed"));
    }

    private static Observable<Integer> error_test_pro(int n) {
        return Observable.fromCallable(() -> error_test(n));
    }

    private static void observable_error_test(int n) {
        Observable.create(observer -> {
            try {
                observer.onNext(n);
                observer.onComplete();
            } catch (Exception e) {
                observer.onError(e);
            }
        }).subscribe(x -> error_test((int) x), Throwable::printStackTrace,
                () -> System.out.println("Emission completed"));
    }

    private static Integer error_test(int n) {
        if (n == 5) throw new RuntimeException("我就是喜欢来搞惊喜");
        System.out.println("我消费的元素是——> " + n);
        return n + 10;
    }

    private static void log(Object msg) {
        System.out.println(Thread.currentThread().getName() + ": " + msg);
    }
}
