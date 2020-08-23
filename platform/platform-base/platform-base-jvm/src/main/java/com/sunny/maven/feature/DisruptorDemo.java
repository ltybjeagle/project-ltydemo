package com.sunny.maven.feature;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.nio.ByteBuffer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/8/16 14:03
 * @description：内存消息队列
 * @modified By：
 * @version: 1.0.0
 */
public class DisruptorDemo {
    public static void main(String ...args) throws Exception {
        // 创建线程池
        Executor executor = Executors.newCachedThreadPool();
        // 事件工厂
        LongEventFactory factory = new LongEventFactory();
        // ringBuffer 的缓冲区的大小是1024
        int bufferSize = 1024;
        // 创建一个disruptor
        // ProducerType.MULTI:创建一个环形缓冲区支持多事件发布到一个环形缓冲区
        // 消费者等待策略:
        // 1、BlockingWaitStrategy：这是默认的策略。使用锁和条件进行数据的监控和线程的唤醒。
        // 因为涉及到线程的切换，是最节省CPU，但在高并发下性能表现最糟糕的一种等待策略。
        // 2、SleepingWaitStrategy:会自旋等待数据，如果不成功，才让出cpu，最终进行线程休眠，
        // 以确保不占用太多的CPU数据，因此可能产生比较高的平均延时。比较适合对延时要求不高的场合，
        // 好处是对生产者线程的影响最小。典型的应用场景是异步日志。
        // 3、YieldingWaitStrategy:用于低延时的场合。消费者线程不断循环监控缓冲区变化，在循环内部，
        // 会使用Thread.yield()让出cpu给别的线程执行时间。
        // 4、BusySpinWaitStrategy:开启的是一个死循环监控，消费者线程会尽最大努力监控缓冲区变化，
        // 因此，CPU负担比较大
        Disruptor<LongEvent> disruptor = new Disruptor<>(factory, bufferSize, executor,
                ProducerType.MULTI, new BlockingWaitStrategy());
        // 创建一个消费者
        disruptor.handleEventsWith(new LongEventHandler());
        // 启动并初始化disruptor
        disruptor.start();

        // 获取已经初始化好的ringBuffer
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        // 获取已经初始化好的ringBuffer
        LongEventProducer producer = new LongEventProducer(ringBuffer);
        ByteBuffer bb = ByteBuffer.allocate(8);
        for (long l = 0; true; l++) {
            // 存入数据
            bb.putLong(0, l);
            producer.onData(bb);
            Thread.sleep(1000);
        }
    }
}
// 代表数据的类
class LongEvent{
    private long value;

    public void setValue(long value) {
        this.value = value;
    }
}
// 产生LongEvent的工厂类，它会在Disruptor系统初始化时，构造所有的缓冲区中的对象实例（预先分配空间）
class LongEventFactory implements EventFactory<LongEvent> {

    @Override
    public LongEvent newInstance() {
        return new LongEvent();
    }
}
// 消费者实现为WorkHandler接口，是Disruptor框架中的类
class LongEventHandler implements EventHandler<LongEvent> {
    // onEvent()方法是框架的回调用
    @Override
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
        System.out.println("Event: " + event);
    }
}
// 消费者实现为WorkHandler接口，是Disruptor框架中的类
class LongEventProducer {
    // 环形缓冲区,装载生产好的数据；
    private final RingBuffer<LongEvent> ringBuffer;
    public LongEventProducer(RingBuffer<LongEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    // 将数据推入到缓冲区的方法：将数据装载到ringBuffer
    public void onData(ByteBuffer bb) {
        // 获取下一个可用的序列号
        long sequence = ringBuffer.next();
        try {
            // 通过序列号获取空闲可用的LongEvent
            LongEvent event = ringBuffer.get(sequence);
            // 设置数值
            event.setValue(bb.getLong(0));
        } finally {
            // 数据发布，只有发布后的数据才会真正被消费者看见
            ringBuffer.publish(sequence);
        }
    }
}

