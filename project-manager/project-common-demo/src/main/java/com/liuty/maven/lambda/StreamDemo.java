package com.liuty.maven.lambda;

import com.liuty.maven.lambda.entity.Dish;
import org.omg.IOP.IOR;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class StreamDemo {
    private static List<Dish> list = Arrays.asList(
            new Dish("pork", false, 800, Dish.Type.MEAT),
            new Dish("beef", false, 700, Dish.Type.MEAT),
            new Dish("chicken", false, 400, Dish.Type.MEAT),
            new Dish("french fries", true, 530, Dish.Type.OTHER),
            new Dish("rice", true, 350, Dish.Type.OTHER),
            new Dish("season fruit", true, 120, Dish.Type.OTHER),
            new Dish("pizza", true, 550, Dish.Type.OTHER),
            new Dish("prawns", false, 300, Dish.Type.FISH),
            new Dish("salmon", false, 450, Dish.Type.FISH));

    public static void main(String ...args) throws Exception {
        List<String> threeHighCaloricDishNames = list.stream().filter(d -> d.getCalories() > 300)
                .map(Dish::getName).limit(3).collect(toList());
        System.out.println(threeHighCaloricDishNames);
        if (list.stream().anyMatch(Dish::isVegetarian)) {
            System.out.println("The menu is (somewhat) vegetarian friendly!!");
        }
        Optional<Dish> dish = list.stream().filter(Dish::isVegetarian).findAny();
        if (dish.isPresent()) {
            System.out.println(dish.get().getName());
        }
        list.stream().filter(Dish::isVegetarian).findAny().ifPresent(d -> System.out.println(d.getName()));
        int[] numbers = {1,2,3,4,5,6,7,8,9};
        int product = Arrays.stream(numbers).reduce(0, (a, b) -> a + b);
        System.out.println(product);
        Arrays.stream(numbers).reduce(0, Integer::sum);
        Integer[] number = {1,2,3,4,5,6,7,8,9};
        Optional<Integer> max = Arrays.stream(number).reduce(Integer::max);
        max.ifPresent(i -> System.out.println(i));
        System.out.println(list.stream().mapToInt(Dish::getCalories).sum());
        IntStream intStream = list.stream().mapToInt(Dish::getCalories); // 转成数值流
        Stream<Integer> stream = intStream.boxed();  // 转成Stream
        // 勾股定理 a*a + b*b = c*c
        Stream<int[]> pythagoreanTriples =
                IntStream.rangeClosed(1, 100).boxed().flatMap(a ->
                        IntStream.rangeClosed(a, 100).filter(b -> Math.sqrt(a * a + b * b) % 1 == 0)
                        .mapToObj(b -> new int[]{a, b, (int)Math.sqrt(a * a + b * b)}));
        pythagoreanTriples.limit(5).forEach(t -> System.out.println(t[0] + "," + t[1] + "," + t[2]));

        Stream<String> stringStream = Stream.of("Java 8 ", "Lambdas ", "In ", "Action");
        stringStream.map(String::toUpperCase).forEach(System.out::println);
        Comparator<Dish> dishCaloriesComparator = Comparator.comparing(Dish::getCalories);
        Optional<Dish> mostCalorieDish = list.stream().collect(maxBy(dishCaloriesComparator));
        mostCalorieDish.ifPresent(System.out::println);
        // 1、热量求和
        System.out.println(list.stream().collect(summingInt(Dish::getCalories)));
        // 2、热量求和，等价上面
        //System.out.println(list.stream().collect(reducing(0, Dish::getCalories, (i, j) -> i + j)));
        System.out.println(list.stream().collect(groupingBy(Dish::getType)));
        System.out.println(list.stream().collect(partitioningBy(Dish::isVegetarian)));
        System.out.println("******************************parallel 并行************************");
        System.out.println(ParallelStreams.parallelSum(50));
        System.out.println("parallelSum sum done in : "
                + measureSumPerf(ParallelStreams::parallelSum, 10000000) + "msecs");
        System.out.println("sequentialSum sum done in : "
                + measureSumPerf(ParallelStreams::sequentialSum, 10000000) + "msecs");
        System.out.println("iterativeSum sum done in : "
                + measureSumPerf(ParallelStreams::iterativeSum, 10000000) + "msecs");
        List<Integer> InNumbers = Arrays.asList(2, 3, 4, 5);
        // peek函数式调试信息
        InNumbers.stream()
                .peek(x -> System.out.println("from Stream :" + x))
                .map(x -> x + 17)
                .peek(x -> System.out.println("after map :" + x))
                .filter(x -> x % 2 == 0)
                .peek(x -> System.out.println("after filter :" + x))
                .limit(3)
                .peek(x -> System.out.println("after limit :" + x))
                .forEach(System.out::println);
        Optional<Dish> dishOpt = Optional.empty() ; // null对象
        Dish dishOptNotNull = new Dish("pork", false, 800, Dish.Type.MEAT);
        dishOpt = Optional.of(dishOptNotNull) ; // 非空对象，如果为空，抛空指针异常
        dishOpt = Optional.ofNullable(dishOptNotNull) ; // 可以为空对象
        Optional<String> dishName = dishOpt.map(Dish::getName);
        dishName.get(); //获取数据
        dishName.orElse("other"); //获取数据，如果不存在返回other
        dishName.orElseThrow(Exception::new); //获取数据，如果不存在抛出异常
        dishName.ifPresent(System.out::println);//输出数据
    }

    /**
     * 测试函数
     * @param function 测试行为
     * @param n 测试数据
     * @return
     */
    public static long measureSumPerf(Function<Long, Long> function, long n) {
        long fastTest = Long.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            long sum = function.apply(n);
            long end = System.nanoTime();
            long duration = (end - start) / 1000000;
            System.out.println("sum=" + sum);
            if (fastTest > duration) {
                fastTest = duration;
            }
        }
        return fastTest;
    }
}
