package com.sunny.maven.observabletest;

import io.reactivex.Observable;
import org.junit.Test;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/12/17 0:17
 * @description：异常处理
 * @modified By：
 * @version: 1.0.0
 */
public class ObservableErrorTest implements ObservableLog {

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

    /**
     * 异常处理
     */
    @Test
    public void observable_error_test_acc() {
        observable_error_test(1);
        observable_error_test(5);
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
}
