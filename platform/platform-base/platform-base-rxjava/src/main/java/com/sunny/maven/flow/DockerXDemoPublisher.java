package com.sunny.maven.flow;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/11/1 14:50
 * @description：事件发布端
 * @modified By：
 * @version: 1.0.0
 */
public class DockerXDemoPublisher<T> implements Flow.Publisher<T>,AutoCloseable {
    private final ExecutorService executor;
    private CopyOnWriteArrayList<DockerXDemoSubscription> list = new CopyOnWriteArrayList();

    public void submit(T item) {
        System.out.println("***********开始发布元素 item: " + item + "***********");
        list.forEach(e -> {
            e.future = executor.submit(() -> {
                e.subscriber.onNext(item);
            });
        });
    }

    public DockerXDemoPublisher(ExecutorService executor) {
        this.executor = executor;
    }

    @Override
    public void close() throws Exception {
        list.forEach(e -> {
           e.future = executor.submit(() -> {
               e.subscriber.onComplete();
           });
        });
    }

    @Override
    public void subscribe(Flow.Subscriber<? super T> subscriber) {
        subscriber.onSubscribe(new DockerXDemoSubscription(subscriber, executor));
        list.add(new DockerXDemoSubscription(subscriber, executor));
    }

    static class DockerXDemoSubscription<T> implements Flow.Subscription {
        private final Flow.Subscriber<? super T> subscriber;
        private final ExecutorService executor;
        private Future<?> future;
        private T item;
        private boolean completed;

        public DockerXDemoSubscription(Flow.Subscriber<? super T> subscriber, ExecutorService executor) {
            this.subscriber = subscriber;
            this.executor = executor;
        }

        @Override
        public void request(long n) {
            if (n != 0 && !completed) {
                if (n < 0) {
                    IllegalArgumentException ex = new IllegalArgumentException();
                    executor.execute(() -> subscriber.onError(ex));
                } else {
                    future = executor.submit(() -> {
                        subscriber.onNext(item);
                    });
                }
            } else {
                subscriber.onComplete();
            }
        }

        @Override
        public void cancel() {
            completed = true;
            if (future != null && !future.isCancelled()) {
                this.future.cancel(true);
            }
        }
    }

    public static void main(String ...args) throws Exception {
        ExecutorService execService = ForkJoinPool.commonPool();
        try (DockerXDemoPublisher<Integer> publisher = new DockerXDemoPublisher<>(execService)) {
            demoSubscribe(publisher, "One");
            demoSubscribe(publisher, "Two");
            demoSubscribe(publisher, "Three");
            IntStream.range(1, 5).forEach(publisher::submit);
        } finally {
            try {
                execService.shutdown();
                int shutdownDelaySec = 1;
                System.out.println("............等待 " + shutdownDelaySec + " 秒后结束服务....");
                execService.awaitTermination(shutdownDelaySec, TimeUnit.SECONDS);
            } catch (Exception ex) {
                System.out.println("捕捉到execService.awaitTermination()方法的异常： " + ex.getClass().getName());
            } finally {
                System.out.println("调用execService.shutdownNow()结束服务...");
                List<Runnable> l = execService.shutdownNow();
                System.out.println("还剩 " + l.size() + " 个任务等待执行，服务已经关闭 ");
            }
        }
    }

    private static void demoSubscribe(DockerXDemoPublisher<Integer> publisher, String subscriberName) {
        DockerXDemoSubscriber<Integer> subscriber = new DockerXDemoSubscriber<>(4L, subscriberName);
        publisher.subscribe(subscriber);
    }
}
