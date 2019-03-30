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
 * 类加载器：负载加载类（字节码）到内存（分：启动类加载器和自定义加载器）
 * ，即在需要的时候将类文件和对应的Class对象加载到内存中（懒加载）。
 *      1、每个Class对象都包含一个加载自身类对象的加载器的引用。
 *      2、使用根类加载器加载进来的类对象，此类对象的加载器应用是NULL。
 *      3、JVM会根据一定规则预判，进行预加载类。
 *      4、类加载器（ClassLoader）可以获取一个文件的输入流（getResourceAsStream("文件路径")）。
 * 类加载过程：ClassLoader是加载java字节码的工具（类里的静态成员和静态块是在类加载的过程中初始化）。
 *      1、加载：查找并加载类的二进制数据。
 *          a、将字节码文件二进制数据加载到内存。
 *          b、JVM创建一个CLASS对象数据结构（描述类的数据结构），放到方法区内。
 *          c、在堆内存储了CLASS对象在方法区数据结构的封装（CLASS对象）
 *          ，并提供了访问方法区数据结构的接口（反射接口、加载类的映射）。
 *          d、向上查找，如果有父类，先加载父类，在加载子类。
 *      2、连接：
 *          a、验证：确保被加载的类的正确性（验证类结构、语义、字节码、二进制兼容性）
 *          b、准备：为类的静态变量分配内存，并将初始化为默认值
 *          c、解析：把类中的符号引用转换为直接引用（将对象引用换成方法区内存地址（指针））。
 *      3、初始化：为类的静态变量赋予正确的初始值（或执行静态代码块）。
 *          a、静态变量的初始化构成是按照顺序从上到下初始化的
 *          （代码块和显示成员变量初始化是按照顺序执行的）。
 *          b、如果静态变量是编译时常量（static final int = 2），即不会执行初始化过程。
 *          c、显示的使用接口的静态变量，才会导致接口初始化。
 *          d、只有访问的静态变量或静态方法确实在当前类的话（子类使用父类的属性和方法不是主动使用）
 *          才是主动使用，才能初始化。
 *          e、使用类加载器的loadClass不是对的类的主动使用（只是将类加载进来），不会初始化。
 *          f、父类成员变量初始化值，父类构造函数执行，子类成员变量初始化，子类构造函数执行
 *          g、初始化顺序：静态变量、非静态变量、构造函数
 * Java程序对类的使用方式分为两种：
 *      1、主动使用（六种）：所有的Java虚拟机实现必须在每个类或接口被java程序“首次主动使用”时
 *      才初始化他们
 *          a、创建类实例（new实例化）。
 *          b、访问某个类或接口的静态变量，或对静态变量赋值。
 *          c、调用类的静态方法
 *          d、反射（Class.forName("")）
 *          e、初始化一个类的子类
 *          f、Java虚拟机启动时被标明为启动类的类
 *      2、被动使用：不会导致类的初始化
 * 类加载器分为两种：
 *      1、Java虚拟机自带的加载器
 *          a、根类加载器（Bootstrap）：C++实现，加载"java.lang.*"等核心库。
 *          b、扩展类加载器（Extension）：Java实现，加载“jre/lib/ext"等扩展类。
 *          ClassLoader.getSystemClassLoader().getParent()
 *          c、系统类加载器/应用类加载器（System）：Java实现
 *          ，加载“环境变量classpath或java.class.path指定的路径下加载的类”。
 *          系统类加载器是自定义加载器的默认父加载器。
 *          ClassLoader.getSystemClassLoader()
 *      2、用户自定义的类加载器
 *          a、java.lang.ClassLoader的子类（用户可以定制类的加载方式）
 * 类加载器过程采用父委托机制加载：
 *      1、每个类加载器都有一个父加载器（除了根类加载器除外）。
 *      2、当有一个加载器要加载一个类的时候，委托他的父加载器进行加载
 *      （如果父加载器还有父加载器则继续向上委托），当父加载器可以加载这个类的时候就由父加载器加载
 *      ，加载完结束，如果父加载器不能加载的话，在由子加载器进行加载。
 *      3、子加载器不一定继承父加载器：
 *      加载器父子关系（父-子）：根类加载器<—扩展类加载器<—系统类加载器<—自定义加载器。
 *      4、类加载器加载了一个类，即该类加载器是加载类的定义类加载器，该加载器及子加载器为初始化类加载器。
 *      5、同一个加载器类的两个实例也可能是父子加载器关系，在类加载器里封装了父类加载器对象。
 *      6、不同加载器加载的类之间是不可见的，不能相互调用（只能通过反射进行调用）。
 *      7、JVM自带的类加载器加载的类是不会被卸载的（自定义的类加载器加载的类会被卸载）。
 * 线程上下文类加载器：越过类加载器的双亲委派机制去加载类，如serviceloader实现
 *      1、使用线程上下文类加载器加载类，要注意保证多个需要通信的线程间的类加载器应该是同一个
 *      ，防止因为不同的类加载器导致类型转换异常(ClassCastException)
 * 对象创建：
 *      1、类加载检查
 *      2、分配内存：
 *          分配方式：指针碰撞、空闲列表，采用方式是根据堆内存情况决定，同时根据垃圾收集算法有关
 *          并发分配问题：CAS + 重试、TLAB(线程私有)
 *      3、初始化零值
 *      4、设置对象头
 *      5、init方法
 *
 * 内存模型：主内存和线程内存。
 *      主内存：变量存在的位置（访问慢）。
 *      线程内存：保存变量的副本，CPU执行指令的时候操作变量副本（访问快）。
 * 寄存器：存储速率最快，空间小，位置在处理器中。线程私有
 * CPU锁：
 *      1、总线锁
 *      2、缓存锁(MESI缓存一致性协议)
 * 内存屏障：
 *      1、loadload
 *      2、loadstore
 *      3、storestore
 *      4、storeload
 *
 * 垃圾收集器：
 *      1、垃圾回收只有在内存资源濒临耗尽的情况下执行，如果没有可能永远都不会执行垃圾回收。
 *      2、JAVA虚拟机在垃圾回收的同时，又对内存中的其他对象进行重新排序，以保证空闲内存的控制。
 *      3、垃圾回收器只负责内存回收，其他的清理工作，只有自己实现清理方法处理（不能使用finalize方法）。
 *      4、垃圾回收对象：要经过两次标记，才真正的回收对象
 *      （第一次标记后，对象依然存在，第二次标记后，才回收）。
 *      5、方法区（永久代）也执行垃圾回收（回收失效常量和无用类）。
 *      6、分代收集算法：分新生代和老年代。
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
