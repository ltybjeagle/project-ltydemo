总体概要:
设计目标：
    1、一站式轻量级企业应用解决方案（框架、平台），基于POJO。
    2、解耦（对象——>IOC容器——>对象）。
    3、基于接口开发理念。

层级关系：
    1、核心（IOC、AOP）
        AOP实现：
        a、集成了AspectJ框架
        b、代理工厂模式：JVM动态代理实现、第三方类库CGLIB实现（基于字节码）
    2、组件（事务、MVC、JDBC、ORM、远程调用）
    3、应用

IOC容器：
    1、控制反转：对象的创建、依赖、撤销由容器管理（生命周期），解耦
    2、依赖注入（DI）：接口注入、属性注入、构造器注入

IOC容器实现：
    1、BeanFactory
        a、BeanDefinition：定义形式，如XML文件
        b、FactoryBean：工厂Bean
        c、scope：singleton、prototype
        d、实现类：DefaultListableBeanFactory、XmlBeanFactory
    2、ApplicationContext
        a、实现类：FileSystemXmlApplicationContext，方法：refresh()

IOC容器初始化：
    1、Resource定位
    2、BeanDefinition载入、解析
    3、向IOC容器注册BeanDefinition，注册到HashMap里
        a、第一次使用getBean方法获取Bean的时候注入依赖
        b、lazy-init属性设置可以预实例化过程，在初始化的时候注入
        c、populateBean实现Bean注入操作
    4、定义BEAN实现了InitializingBean接口，会在初始化的时候执行afterPropertiesSet方法

AOP实现方式：ProxyFactoryBean生成代理对象
    1、JDK动态代理（反射）：实现invocationHandler接口
    2、CGLIB字节码：
    3、AspectJ框架：

Advice通知：
    1、BeforeAdvice前置通知
    2、AfterAdvice后置通知
    3、ThrowsAdvice异常通知

Pointcut切点：正则表达式

Advisor通知器：
    1、连接Advice通知和Pointcut切点
    2、使用Adapter适配

SpringMVC相关：
DispatcherServlet前置处理器：
    1、HandlerMapping映射关系、HandlerExecutionChain对象
    2、HandlerAdapter、HandlerInterceptor
    3、ViewResolver

事务管理：基于AOP实现
    1、TransactionAttributeSourceAdvisor：
        TransactionAttribute
    2、TransactionInterceptor

事务管理器（TransactionManager）：
    1、TransactionDefinition
    2、TransactionInfo
        TransactionStatus

事务传播特性：

事务隔离特性：