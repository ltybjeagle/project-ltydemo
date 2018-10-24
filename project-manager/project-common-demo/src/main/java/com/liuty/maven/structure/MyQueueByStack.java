package com.liuty.maven.structure;

import java.util.Stack;

/**
 * 基于栈实现队列功能
 * @param <T>
 */
public class MyQueueByStack<T> {

    private Stack<T> stackPush = new Stack<>();
    private Stack<T> stackPop = new Stack<>();

    /**
     * 入队操作
     * @param t
     */
    public void enQueue(T t) {
        stackPush.push(t);
    }

    /**
     * 出队操作
     * @return
     */
    public T deQueue() {
        if (stackPop.isEmpty()) {
           if (stackPush.isEmpty()) {
               return null;
           }
           transfer();
        }
        return stackPop.pop();
    }

    /**
     * stackPush栈数据迁移到stackPop栈
     */
    public void transfer() {
        while (!stackPush.isEmpty()) {
            stackPop.push(stackPush.pop());
        }
    }
}
