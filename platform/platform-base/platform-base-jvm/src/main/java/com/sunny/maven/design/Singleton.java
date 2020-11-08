package com.sunny.maven.design;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/8/23 23:01
 * @description：单例模式
 * @modified By：
 * @version: 1.0.0
 */
public class Singleton {
    public static void main(String ...args) {
    }

    /**
     * 饿汉式
     */
    static class Signleton01 {
        // 私有构造函数，防止别人实例化
        private Signleton01(){}
        // 静态属性，指向一个实例化对象
        private static final Signleton01 INSTANCE = new Signleton01();
        // 公共方法，以便别人获取到实例化对象属性
        public static Signleton01 getINSTANCE() {
            return INSTANCE;
        }
    }
    /**
     * 懒汉式
     */
    static class Singleton02 {
        // 私有构造函数，防止别人实例化
        private Singleton02() {}
        // 静态属性，指向一个实例化对象（注意，这里没有实例化对象哦）
        private static Singleton02 INSTANCE;
        // 公共方法，以便别人获取到实例化对象属性
        public static Singleton02 getINSTANCE() {
            if (INSTANCE == null) {
                INSTANCE = new Singleton02();
            }
            return INSTANCE;
        }
    }
    /**
     * synchronized 写法
     */
    static class Singleton03 {
        private Singleton03() {}
        private static Singleton03 INSTANCE;

        // 注意，这里静态方法加了synchronized关键字
        public synchronized static Singleton03 getINSTANCE() {
            if (INSTANCE == null) {
                INSTANCE = new Singleton03();
            }
            return INSTANCE;
        }
    }
    /**
     * 双重检测
     */
    static class Singleton04 {
        private Singleton04() {}
        // 注意，使用双重检验写法要加上volatile关键字，避免指令重排（有个印象就行，这不是本文的重点）
        private static volatile Singleton04 INSTANCE;

        public static Singleton04 getINSTANCE() {
            if (INSTANCE == null) {
                // 只有在对象还没有实例化的时候才上锁
                synchronized (Singleton04.class) {
                    // 额外加一层判断
                    if (INSTANCE == null) {
                        INSTANCE = new Singleton04();
                    }
                }
            }
            return INSTANCE;
        }
    }
    /**
     * 内部类
     */
    static class Singleton05 {
        // 老套路，将构造函数私有化
        private Singleton05() {}
        // 声明一个内部类，内部类里持有实例的引用
        private static class Inner {
            public static final Singleton05 INSTANCE = new Singleton05();
        }
        // 公共方法
        public static Singleton05 getINSTANCE() {
            return Inner.INSTANCE;
        }
    }
    /**
     * 枚举
     */
    public enum Singleton06 {
        // 实例
        INSTANCE;
        // 公共方法
        public static Singleton06 getINSTANCE() {
            return INSTANCE;
        }
    }
}
