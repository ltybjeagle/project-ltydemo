package com.liuty.maven.features;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FutureDemo {
    private static List<Shop> shops = Arrays.asList(new Shop("BestPrice"),
            new Shop("LetsSaveBig"),
            new Shop("MyFavoriteShop"),
            new Shop("BuyItAll"),
            new Shop("BuyItAll1"));
    private static final Executor executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100)
            , new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            return thread;
        }
    });
    public static void main(String ...args) {
        Shop shop = new Shop("BestShop");
        long startTime = System.nanoTime();
        Future<Double> futurePrice = shop.getPriceAsync("my favorite product");
        long invocationTime = ((System.nanoTime() - startTime) / 1_000_000);
        System.out.println("Invocation returned after " + invocationTime + " msecs");
        doSomeTingElse();
        try {
            double price = futurePrice.get();
            System.out.printf("Price is %.2f%n", price);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        long retrievalTime = ((System.nanoTime() - startTime) / 1_000_000);
        System.out.println("Price returned after " + retrievalTime + " msecs");
        System.out.println("#############################################################");
        long start = System.nanoTime();
        System.out.println(findPrices("myPhone27S"));
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + " msecs");
        System.out.println("################################[1]#############################");
        start = System.nanoTime();
        System.out.println(findPricesByCompletableFuture("myPhone27S"));
        duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + " msecs");
        System.out.println("################################[2]#############################");
        long startTimes = System.nanoTime();
        CompletableFuture[] futures = findPricesStream("myPhone27S")
                .map(f -> f.thenAccept(s ->
                        System.out.println(s + "(done in" + ((System.nanoTime() - startTimes) / 1_000_000)
                                + " msecs)"))).toArray(size -> new CompletableFuture[size]);
        CompletableFuture.allOf(futures).join();
        System.out.println("All shops have now responded in "
                + ((System.nanoTime() - startTimes) / 1_000_000) + " msecs");
    }

    public static List<String> findPrices(String product) {
        return shops.parallelStream().map(shop ->
                String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
                .collect(Collectors.toList());
    }

    public static List<String> findPricesByCompletableFuture(String product) {
        List<CompletableFuture<String>> priceFutures = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(()
                        -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)), executor))
                .collect(Collectors.toList());
        return priceFutures.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

    public static Stream<CompletableFuture<String>> findPricesStream(String product) {
        return shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(
                        () -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)), executor));
    }

    public static void doSomeTingElse() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }
}

class Shop {
    private static final ThreadLocalRandom random = ThreadLocalRandom.current();
    private String name;

    public Shop(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * 同步调用
     * @param product
     * @return
     */
    public double getPrice(String product) {
        return calculatePrice(product);
    }

    /**
     * 异步实现，CompletableFuture工厂方法实现，效果同getPriceAsync
     * @param product
     * @return
     */
    public Future<Double> getPriceAsyncByFactory(String product) {
        return CompletableFuture.supplyAsync(() -> calculatePrice(product));
    }

    /**
     * 异步实现
     * @param product
     * @return
     */
    public Future<Double> getPriceAsync(String product) {
        CompletableFuture<Double> future = new CompletableFuture<>();
        new Thread(() -> {
            try {
                double price = calculatePrice(product);
                future.complete(price);
            } catch (Exception ex) {
                future.completeExceptionally(ex);
            }
        }).start();
        return future;
    }

    public double calculatePrice(String product) {
        delay();
        return random.nextDouble() * product.charAt(0) + product.charAt(1);
    }

    public static void delay() {
        int delay = 500 + random.nextInt(2000);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }
}