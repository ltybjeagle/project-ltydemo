package com.liuty.maven.design;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * RxJava实现观察者模式
 */
public class ObservableDesign {

    public static void main(String ...args) {
        // 被观察者（主题）
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> observableEmitter) throws Exception {
                observableEmitter.onNext("Hello RxJava");
                observableEmitter.onComplete();
            }
        });
        // 观察者
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
        // 订阅观察主题
        observable.subscribe(observer);
    }
}
