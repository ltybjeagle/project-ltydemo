package com.sunny.maven.core.utils.aqs;

import com.sunny.maven.core.utils.async.Depot;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author SUNNY
 * @description: ReentrantLock测试
 * @create: 2022-08-16 16:35
 */
@Slf4j
public class ReentrantLockTest {
    @Test
    public void ReentrantLockConditionTest() throws InterruptedException {
        Depot depot = new Depot(500);
        new Producer(depot).Produce(500);
        new Producer(depot).Produce(200);
        new Consumer(depot).consume(500);
        new Consumer(depot).consume(200);
        Thread.sleep(10000);
    }

    class Consumer {
        private Depot depot;
        public Consumer(Depot depot) {
            this.depot = depot;
        }
        public void consume(int no) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    depot.consume(no);
                }
            }, no + " consume thread").start();
        }
    }

    class Producer {
        private Depot depot;
        public Producer(Depot depot) {
            this.depot = depot;
        }
        public void Produce(int no) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    depot.produce(no);
                }
            }, no + " produce thread").start();
        }
    }
}
