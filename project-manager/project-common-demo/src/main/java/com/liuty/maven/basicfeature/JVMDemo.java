package com.liuty.maven.basicfeature;

/**
 * HotSpot VM是32位JVM，内存地址空间限制在4G
 * 32位系统，JAVA堆的大小受限于操作系统：
 *      1、Windows操作系统，JAVA堆大小1.5G
 *      2、Linux操作系统，JAVA堆大小2.5G到3.0G
 * 64位HotSpot VM增大了JAVA堆，并使用压缩指针的方式提供了32位JVM的性能。
 *      1、内存地址：16进制数表示
 *      2、包含三个组件：VM运行时、JIT编译器、内存管理。
 * HotSpot VM运行时担当的职责：
 *      1、命令行选项解析：通过启动命令添加一些内部参数设置，对于布尔类型的参数使用参数前面增加+、-号确认。
 *      命令行选项类型：
 *          a、标准选项：所有JVM都要实现的选项。
 *          b、非标准选项（以-X为前缀）：不保证、也不强制JVM都支持。
 *          c、非稳定选项（以-XX为前缀）：为了特定需要而对JVM进行的矫正，可能需要有系统配置参数的访问权限，
 *          同非标准选项一样，不强制。
 *      2、VM生命周期管理：负责JVM的启动和停止，使用启动器组件启动JVM（启动器：java、javaw、javaws）。
 *      3、类加载：这个过程包含加载（查找字节码文件，JVM验证字节码文件逻辑）、链接（验证语义）和初始化。
 *          a、类加载器分为启动类加载器、扩展类加载器，应用类加载器。
 *          b、类加载时，会在永久代存储类元数据（Class对象结构）。
 *      4、字节码解析
 *      5、异常处理
 *      6、同步：JVM使用monitor对象保障线程运行代码之间的互斥。
 *      7、线程管理
 *      8、Java本地接口
 *      9、VM致命错误处理和C++（非JAVA）堆管理（系统崩溃异常会生成错误日志hs_err_pid.log）
 *      10、解释器：Java解释器负责.class文件的查找、解释、加载。
 *
 * 即时编译器（JIT）：将CLASS字节码解析成机器执行指令（解析过程进行优化、指令重排序）
 *      1、JIT编译器（运行时）
 *
 * 常量池：
 *      1、CLASS常量池：字节码文件包含类的常量池（字面量、符号引用），供运行时使用
 *      2、字符串常量池
 *
 * 内存结构：程序计数器、栈（stack）、方法区（静态域）、堆（heap）
 *      1、程序计数器：每个线程都有一个程序计数器，记录下一条执行的指令。是线程私有内存区域。
 *      JAVA程序的话，程序计数器记录的是正在执行的JAVA字节码的地址，NATIVE方法的话，计数器是空的。
 *      2、虚拟机栈：管理JAVA方法运行的内存模型，是线程私有的，主要存储局部变量以及对象的引用，速率较快。
 *          a、正常方法调用：将参数压入栈，调转到方法处执行，在跳回栈清理参数，处理返回值。
 *          b、JVM虚拟机JIT编译器的栈上分配（对象的内存分配在栈上，随着出栈内存随即回收）。
 *          c、特点是先进后出
 *      3、本地方法区：同虚拟机栈，是线程私有的，管理的是JAVA的本地方法。
 *      4、方法区：是线程公有的，存储加载的类元数据的信息，如常量区（存储常量数据）、静态区、
 *      即时编译器编译后的代码，是永久代。
 *      5、JAVA堆：存储JAVA对象和数组，速率慢，是线程共有的，分年轻代和老年代，垃圾回收的主要区域。
 *
 * 对象创建：
 *      1、类加载检查
 *      2、分配内存：
 *          分配方式：指针碰撞、空闲列表，采用方式是根据堆内存情况决定，同时根据垃圾收集算法有关
 *          并发分配问题：CAS + 重试、TLAB(线程私有)
 *      3、初始化零值
 *      4、设置对象头
 *      5、init方法
 *
 * JVM参数：
 *      -XX:+TraceClassLoading  输出类加载信息
 *      -XX:+HeapDumpOnOutOfMemoryError  堆内存溢出时导出堆信息（可以用MAT工具分析问题）
 *      -XX:HeapDumpPath  堆内存溢出时导出文件的路径
 *      -XX:+PrintGC  设置日志输出GC简要信息
 *      -XX:+PrintGCDetails  设置日志输出GC详细信息（新生代、老年代、永久代信息）
 *      -XX:+PrintHeapAtGC  设置日志输出GC前后详细信息
 *      -XX:+PrintGCTimeStamps  设置日志输出GC时间（虚拟机启动时间偏移量）
 *      -XX:+PrintGCApplicationConcurrentTime  设置日志输出应用程序的执行时间
 *      -XX:+PrintGCApplicationStoppedTime  设置日志输出应用程序因GC产生的停顿时间
 *      -Xloggc:log/gc.log  设置将日志输出到文件（当前目录下的log文件夹）
 *      -verbose:class  设置日志输出类的加载和卸载
 *      -XX:+TraceClassUnLoading  设置日志输出类的卸载信息
 *      -XX:+PrintClassHistogram  设置日志输出类的当前信息
 *      -XX:+PrintVMOptions  设置日志输出虚拟机启动的时候接受的命令行显式参数
 *      -XX:+PrintCommandLineFlags  设置日志输出虚拟机启动的时候接受的命令行显式和隐式参数
 *      -XX:+PrintFlagsFinal  设置日志输出虚拟机启动的时候接受的全部的参数
 *      -Xmx  设置虚拟机堆最大值
 *      -Xms  设置虚拟机堆初始化值
 *      -Xmn  设置虚拟机堆新生代大小值
 *      -XX:survivorRatio  设置虚拟机堆新生代eden/from(to)比例
 *      -XX:NewRatio  设置虚拟机堆老年代/新生代比例
 *      -XX:PermSize  设置虚拟机方法区（永久代）初始大小值（jdk1.6或jdk1.7）
 *      -XX:MaxPermSize  设置虚拟机方法区（永久代）最大值（jdk1.6或jdk1.7）
 *      -XX:MaxMetaspaceSize  设置虚拟机方法区（永久代）最大值（jdk1.8）
 *      -XX:MaxDirectMemorySize  设置虚拟机直接内存最大值（如果不设置，默认使用堆设置Xmx）
 *      -Xss  设置虚拟机栈大小
 *      -server  设置虚拟机服务端模式服务启动
 *      -client  设置虚拟机客户端模式服务启动
 *      -XX:+UseSerialGC  设置虚拟机新生代和老年代使用串行垃圾回收器
 *      -XX:+UseParNewGC  设置虚拟机新生代使用ParNew（并行）垃圾回收器、老年代使用串行垃圾回收器
 *      -XX:+UseParallelGC  设置虚拟机新生代使用ParallelGC垃圾回收器、老年代使用串行垃圾回收器
 *      -XX:+UseConcMarkSweepGC  设置虚拟机新生代使用ParNew（并行）垃圾回收器、
 *      老年代使用CMS（并发）垃圾回收器
 *      -XX:ParallelGCThreads  设置虚拟机ParNew、G1垃圾回收器工作时的线程数（建议同CPU数）
 *      -XX:+UseParallelOldGC  设置虚拟机新生代使用ParallelGC垃圾回收器、
 *      老年代使用ParallelOldGC垃圾回收器
 *      -XX:MaxGCPauseMillis  设置虚拟机ParallelGC、G1垃圾回收器的最大停顿时间
 *      -XX:GCTimeRatio  设置虚拟机ParallelGC垃圾回收器吞吐量大小
 *      -XX:+UseAdaptiveSizePolicy  设置虚拟机ParallelGC垃圾回收器启动自适应模式（自动调休优化配置）
 *      -XX:-CMSPrecleaningEnabled  设置虚拟机CMS（并发）垃圾回收器关闭预清理环节操作
 *      -XX:ConcGCThreads  设置虚拟机CMS（并发）垃圾回收器工作时的线程数
 *      -XX:ParallelCMSThreads  设置虚拟机CMS（并发）垃圾回收器工作时的线程数
 *      -XX:CMSInitiatingOccupancyFraction  设置虚拟机CMS（并发）垃圾回收器执行阀值
 *      （默认68，即老年代使用率达到68%开启一次CMS垃圾回收）
 *      -XX:+UseCMSCompactAtFullCollection  设置虚拟机CMS（并发）垃圾回收器打开垃圾回收后
 *      执行一次内存碎片压缩整理
 *      -XX:CMSFullGCsBeforeCompaction  设置虚拟机CMS（并发）垃圾回收器执行多少次垃圾回收后
 *      执行一次内存碎片压缩整理
 *      -XX:+CMSClassUnloadingEnabled  设置虚拟机CMS（并发）垃圾回收器打开永久代回收开关回收类元数据
 *      -XX:+UseG1GC  设置虚拟机使用G1垃圾回收器
 *      -XX:InitiatingHeapOccupancyPercent  设置虚拟机G1垃圾回收器标记周期执行阀值
 *      （默认45，即堆使用率达到45%执行标记周期）
 *      -XX:+DisableExplicitGC  设置System.gc()代码失效
 *      -XX:+ExplicitGCInvokesConcurrent  设置System.gc()代码出发的FULL GC使用并发方式回收
 *      （System.gc()默认非并发方式回收）
 *      -XX:-ScavengeBeforeFullGC  关闭FULL GC的时候先执行一次新生代GC操作
 *      （默认并发回收器，执行FULL GC的时候默认先执行一次新生代GC）
 *      -XX:MaxTenuringThreshold  设置新生代晋升老年代年龄
 *      （如：15，新生代对象经历15次GC后依然存在就转到老年代）
 *      -XX:TargetSurvivorRatio  设置新生代Survivor控件使用率为多少时，对象直接晋升到老年代
 *      -XX:PretenureSizeThreshold  设置固定大小，大于此值的对象直接晋升老年代
 *      -XX:-UseTLAB  禁用TLAB
 *      -XX:+UseTLAB  使用TLAB
 * 参数设置样例：
 *      1、-Xms20m -Xmx20m：设置堆内存最小、最大值20m
 *      2、-XX:+HeapDumpOnOutOfMemoryError：dump出当前堆转储快照
 *      3、-Xss128k:设置栈空间大小
 *      4、-XX:PermSize=10m -XX:MaxPermSize=10m:设置方法区空间
 *      5、-Xmx20m -XX:MaxDirectMemorySize=10m：设置直接内存空间，如果没有设置则默认堆最大空间
 */
public class JVMDemo {

    public static void main(String ...args) {
        System.out.println("JVM参数测试");
    }
}
