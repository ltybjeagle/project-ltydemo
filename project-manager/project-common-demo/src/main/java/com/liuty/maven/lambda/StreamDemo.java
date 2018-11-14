package com.liuty.maven.lambda;

import com.liuty.maven.lambda.entity.Dish;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
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

    public static void main(String ...args) {
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
    }
}
