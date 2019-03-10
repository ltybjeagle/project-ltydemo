package com.liuty.maven.multithread;
/**/
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by liutianyang on 2018/11/11.
 */
public class ThreadLocalRandomTest {

    public static void main(String ...args) {
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        for (int i = 0; i < 10; i++) {
            System.out.println(threadLocalRandom.nextInt(5));
        }
    }
}
