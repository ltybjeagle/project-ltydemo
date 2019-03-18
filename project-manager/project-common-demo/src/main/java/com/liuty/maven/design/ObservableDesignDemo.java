package com.liuty.maven.design;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 观察者模式
 * 结构：
 *      1、设置主题
 *      2、观察者订阅主题
 *      3、提供主题信息
 *      4、主题推送消息到各个观察者
 */
public class ObservableDesignDemo {

    public static void main(String ...args) {
        /**
         * RxJava（异步编程）实现观察者模式
         *      1、Observable<String> 实例化被观察者（主题）
         *      2、Observer<String> 实例化观察者
         *      3、observable.subscribe(observer) 订阅观察主题
         */
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> observableEmitter) throws Exception {
                observableEmitter.onNext("Hello RxJava");
                observableEmitter.onComplete();
            }
        });
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable disposable) {
            }
            @Override
            public void onNext(String s) {
                System.out.println("subscribe:" + s);
            }

            @Override
            public void onError(Throwable throwable) {
            }

            @Override
            public void onComplete() {
            }
        };
        observable.subscribe(observer);
    }
}
