package com.liuty.maven.structure;

import org.junit.Test;

public class MyQueueByStackTest {

    @Test
    public void myQueueByStackTest() {
        MyQueueByStack<Integer> queue = new MyQueueByStack<Integer>();
        queue.enQueue(1);
        queue.enQueue(2);
        queue.enQueue(3);
        System.out.println(queue.deQueue());
        System.out.println(queue.deQueue());
        queue.enQueue(4);
        System.out.println(queue.deQueue());
        System.out.println(queue.deQueue());
    }
}
