package com.sunny.maven.observabletest;

import io.reactivex.Observable;
import io.reactivex.internal.functions.ObjectHelper;
import org.junit.Test;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/12/13 19:05
 * @description：Observable定义事件源
 * @modified By：
 * @version: 1.0.0
 */
public class ObservableDemoTest implements ObservableLog {

    /**
     * 订阅事件源
     */
    @Test
    public void observable() {
        Observable<Object> observable = Observable.create(observer -> {
            observer.onNext("处理的数字是：" + Math.random() * 100);
            observer.onComplete();
        });
        observable.subscribe(consumer -> {
            System.out.println("我处理的元素是：" + consumer);
        });
        observable.subscribe(consumer -> {
            System.out.println("我处理的元素是：" + consumer);
        });
    }

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

    /**
     * 主线程执行（一个执行完，在执行第二个）
     */
    @Test
    public void rang_test() {
        log("Rang_test Before");
        Observable.range(5, 3).subscribe(this::log);
        log("Rang_test After");
    }

    @Test
    public void just_test() {
        log("Just_test Before");
        Observable.just("Jan", "Feb", "Mar", "Apl", "May", "Jun").subscribe(this::log);
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
}
