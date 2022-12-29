package com.sunny.maven.code;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;
import reactor.util.context.Context;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author SUNNY
 * @description: Reactor测试
 * @create: 2022-09-29 10:00
 */
@Slf4j
public class ReactorTest {

    @Test
    public void testSyncToAsync() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Mono.fromCallable(this::getSyncString).
                subscribeOn(Schedulers.boundedElastic()).
                subscribe(System.out::println, null, countDownLatch::countDown);
        countDownLatch.await(10, TimeUnit.SECONDS);
    }

    private String getSyncString() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        return "Hello, Reactor!";
    }

    @Test
    public void testViaStepVerifier() {
        StepVerifier.create(generateFluxFrom1To6()).
                expectNext(1, 2, 3, 4, 5, 6).
                expectComplete().
                verify();
        StepVerifier.create(generateMonoWithError()).
                expectErrorMessage("some error").
                verify();
    }

    private Flux<Integer> generateFluxFrom1To6() {
        return Flux.just(1, 2, 3, 4, 5, 6);
    }

    private Mono<Integer> generateMonoWithError() {
        return Mono.error(new Exception("some error"));
    }

    /**
     * Mono  0-1 个元素
     * Flux  0-N 个元素
     */
    @Test
    public void reactorTest() {
        String[] strs = {"1", "2", "3"};
        // 定义订阅者
        Subscriber<Integer> subscriber = new Subscriber<>() {
            private Subscription subscription;

            @Override
            public void onSubscribe(Subscription subscription) {
                // 保存订阅关系，需要用它来给发布者响应
                this.subscription = subscription;
                // 请求一个数据
                this.subscription.request(1);
            }

            @Override
            public void onNext(Integer integer) {
                // 接收到一个数据，处理
                log.info("接收到数据：{}", integer);
                // 处理完调用request再请求一个数据
                this.subscription.request(1);
            }

            @Override
            public void onError(Throwable throwable) {
                // 出现异常
                throwable.printStackTrace();
                // 通知发布者，后面不接受数据了
                this.subscription.cancel();
            }

            @Override
            public void onComplete() {
                // 全部数据处理完成
                log.info("全部数据处理完成");
            }
        };
        Flux.fromArray(strs).map(Integer::parseInt).subscribe(subscriber);
        Context context = Context.empty();
        Flux.fromArray(strs).map(Integer::parseInt).subscribe(
                item -> log.info("接收到数据：{}", item),
                System.err::println,
                () -> log.info("全部数据处理完成"),
                context);
    }
}
