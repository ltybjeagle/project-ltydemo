package com.sunny.maven.flow;

import java.util.concurrent.Flow;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/11/1 14:32
 * @description：接收端
 * @modified By：
 * @version: 1.0.0
 */
public class DockerXDemoSubscriber<T> implements Flow.Subscriber<T> {
    private String name;
    private Flow.Subscription subscription;
    final long bufferSize;
    long count;

    public String getName() {
        return name;
    }

    public Flow.Subscription getSubscription() {
        return subscription;
    }

    public DockerXDemoSubscriber(long bufferSize, String name) {
        this.bufferSize = bufferSize;
        this.name = name;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        (this.subscription = subscription).request(bufferSize);
        System.out.println("开始onSubscriber订阅");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNext(T item) {
        System.out.println("#####" + Thread.currentThread().getName() +
                " name:" + name + " item:" + item + "#####");
        System.out.println(name + " received: " + item);
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        System.out.println("Completed");
    }
}
