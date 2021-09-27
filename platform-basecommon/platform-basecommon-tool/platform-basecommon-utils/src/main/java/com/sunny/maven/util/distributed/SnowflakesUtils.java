package com.sunny.maven.util.distributed;

/**
 * @author SUNNY
 * @description: 雪花算法（唯一ID，单机）
 * @create: 2021-09-17 23:37
 */
public class SnowflakesUtils {
    /**
     * 获取唯一ID
     * @return long
     */
    public static long uniqueLong() {
        return Snowflakes.SNOW_FLAKES.nextId();
    }
    /**
     * 获取唯一ID
     * @return String
     */
    public static String uniqueLongHex() {
        return String.format("%016x", uniqueLong());
    }

    /**
     * 私有 静态内部类
     */
    private static class Snowflakes {
        /**
         * 内部类对象（单例模式）
         */
        private static final SnowflakesUtils.Snowflakes SNOW_FLAKES = new SnowflakesUtils.Snowflakes();
        /**
         * 开始时间截 (2015-01-01)
         */
        private final long startTimestamp = 1489111610226L;
        /**
         * 机器ID所占位置
         */
        private final long workerIdBits = 5L;
        /**
         * 数据标识所占位数
         */
        private final long dataCenterIdBits = 5L;
        /**
         * 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
         */
        private final long maxWorkerId = ~(-1L << workerIdBits);
        /**
         * 支持的最大数据标识id，结果是31
         */
        private final long maxDataCenterId = ~(-1L << dataCenterIdBits);
        /**
         * 序列在id中占的位数
         */
        private final long sequenceBits = 12L;
        /**
         * 机器ID向左移12位
         */
        private final long workerIdShift = sequenceBits;
        /**
         * 数据标识id向左移17位(12 + 5)
         */
        private final long dataCenterIdShift = sequenceBits + workerIdBits;
        /**
         * 时间截向左移22位(5 + 5 + 12)
         */
        private final long timestampLeftShift = sequenceBits + workerIdBits + dataCenterIdBits;
        /**
         * 生成序列的掩码，这里为4095 (0b111111111111 = 0xfff = 4095)
         */
        private final long sequenceMask = ~(-1L << sequenceBits);
        /**
         * 工作机器ID(0~31)
         */
        private long workerId;
        /**
         * 数据中心ID(0~31)
         */
        private long dataCenterId;
        /**
         * 毫秒内序列(0~4095)
         */
        private long sequence = 0L;
        /**
         * 上次生成ID的时间截
         */
        private long lastTimestamp = -1L;
        /**
         * 构造函数
         */
        private Snowflakes() {
            this.workerId = 0L;
            this.dataCenterId = 0L;
        }
        /**
         * 获得下一个ID (该方法是线程安全的)
         * @return long
         */
        public synchronized long nextId() {
            long timestamp = timeGen();
            // 如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
            if (timestamp < lastTimestamp) {
                throw new RuntimeException(String.format(
                        "Clock moved backwards.  Refusing to generate id for %d milliseconds",
                        lastTimestamp - timestamp));
            }
            // 如果是同一时间生成的，则进行毫秒内序列
            if (lastTimestamp == timestamp) {
                sequence = (sequence + 1) & sequenceMask;
                // 毫秒内序列溢出
                if (sequence == 0L) {
                    // 阻塞到下一个毫秒,获得新的时间戳
                    timestamp = tilNextMillis(lastTimestamp);
                }
            } else {
                // 时间戳改变，毫秒内序列重置
                sequence = 0L;
            }
            // 上次生成ID的时间截
            lastTimestamp = timestamp;
            // 移位并通过或运算拼到一起组成64位的ID
            return ((timestamp - startTimestamp) << timestampLeftShift)
                    | (dataCenterId << dataCenterIdShift)
                    | (workerId << workerIdShift)
                    | sequence;
        }
        /**
         * 阻塞到下一个毫秒，直到获得新的时间戳
         * @param lastTimestamp 上次生成ID的时间截
         * @return long
         */
        protected long tilNextMillis(long lastTimestamp) {
            long timestamp = timeGen();
            while (timestamp <= lastTimestamp) {
                timestamp = timeGen();
            }
            return timestamp;
        }
        /**
         * 返回以毫秒为单位的当前时间
         * @return long
         */
        protected long timeGen() {
            return System.currentTimeMillis();
        }
    }
}
