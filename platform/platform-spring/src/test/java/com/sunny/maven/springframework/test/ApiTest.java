package com.sunny.maven.springframework.test;

import cn.hutool.core.io.IoUtil;
import com.sunny.maven.springframework.beans.PropertyValue;
import com.sunny.maven.springframework.beans.PropertyValues;
import com.sunny.maven.springframework.beans.factory.config.BeanDefinition;
import com.sunny.maven.springframework.beans.factory.config.BeanReference;
import com.sunny.maven.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.sunny.maven.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import com.sunny.maven.springframework.context.ApplicationContext;
import com.sunny.maven.springframework.context.support.ClassPathXmlApplicationContext;
import com.sunny.maven.springframework.core.io.DefaultResourceLoader;
import com.sunny.maven.springframework.core.io.Resource;
import com.sunny.maven.springframework.test.bean.UserDao;
import com.sunny.maven.springframework.test.bean.UserService;
import com.sunny.maven.springframework.test.common.MyBeanFactoryPostProcessor;
import com.sunny.maven.springframework.test.common.MyBeanPostProcessor;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author SUNNY
 * @description: 测试类
 * @create: 2023-02-25 22:40
 */
public class ApiTest {

    @Test
    public void test_BeanFactoryPostProcessorAndBeanPostProcessor() {
        // 1、初始化BeanFactory接口
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2. 读取配置文件&注册Bean
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions("classpath:spring.xml");

        // 3. BeanDefinition 加载完成 & Bean实例化之前，修改 BeanDefinition 的属性值
        MyBeanFactoryPostProcessor myBeanFactoryPostProcessor = new MyBeanFactoryPostProcessor();
        myBeanFactoryPostProcessor.postProcessBeanFactory(beanFactory);

        // 4. Bean实例化之后，修改 Bean 属性信息
        MyBeanPostProcessor myBeanPostProcessor = new MyBeanPostProcessor();
        beanFactory.addBeanPostProcessor(myBeanPostProcessor);

        // 5. 获取Bean对象调用方法
        UserService userService = beanFactory.getBean("userService", UserService.class);
        String result = userService.queryUserInfo();
        System.out.println("测试结果：" + result);
    }

    @Test
    public void test_context_xml() {
        // 1.初始化 BeanFactory
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("classpath:springPostProcessor.xml");

        // 2. 获取Bean对象调用方法
        UserService userService = applicationContext.getBean("userService", UserService.class);
        String result = userService.queryUserInfo();
        System.out.println("测试结果：" + result);
    }

    @Test
    public void test_BeanFactory() {
        // 1、初始化BeanFactory接口
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2、注册 UserDao
        beanFactory.registerBeanDefinition("userDao", new BeanDefinition(UserDao.class));

        // 3、使用 UserService填充属性（id、userDao）
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("id", "10001"));
        propertyValues.addPropertyValue(new PropertyValue("userDao", new BeanReference("userDao")));

        // 4、注册Bean对象
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class, propertyValues);
        beanFactory.registerBeanDefinition("userService", beanDefinition);

        // 5、获取Bean对象
        UserService userService = (UserService) beanFactory.getBean("userService", "SUNNY");
        userService.queryUserInfo();
    }

    @Test
    public void test_xml() {
        // 1.初始化 BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2. 读取配置文件&注册Bean
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions("classpath:spring.xml");

        // 3. 获取Bean对象调用方法
        UserService userService = beanFactory.getBean("userService", UserService.class);
        String result = userService.queryUserInfo();
        System.out.println("测试结果：" + result);
    }


    private DefaultResourceLoader resourceLoader;

    @Before
    public void init() {
        resourceLoader = new DefaultResourceLoader();
    }

    @Test
    public void test_classpath() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:important.properties");
        InputStream inputStream = resource.getInputStream();
        String content = IoUtil.readUtf8(inputStream);
        System.out.println(content);
    }

    @Test
    public void test_cglib() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(UserService.class);
        enhancer.setCallback(new NoOp() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }
        });
        Object obj = enhancer.create(new Class[] {String.class}, new Object[] {"SUNNY"});
        System.out.println(obj);
    }

    @Test
    public void test_newInstance() throws IllegalAccessException, InstantiationException {
        UserService userService = UserService.class.newInstance();
        System.out.println(userService);
    }

    @Test
    public void test_constructor() throws IllegalAccessException, InstantiationException, NoSuchMethodException,
            InvocationTargetException {
        Class<UserService> userServiceClass = UserService.class;
        Constructor<UserService> declaredConstructor = userServiceClass.getDeclaredConstructor(String.class);
        UserService userService = declaredConstructor.newInstance("SUNNY");
        System.out.println(userService);
    }

    @Test
    public void test_parameterTypes() throws Exception {
        Class<UserService> userServiceClass = UserService.class;
        Constructor<?>[] declaredConstructors = userServiceClass.getDeclaredConstructors();
        Constructor<?> constructor = declaredConstructors[0];
        Constructor<UserService> declaredConstructor =
                userServiceClass.getDeclaredConstructor(constructor.getParameterTypes());
        UserService userService = declaredConstructor.newInstance("SUNNY");
        System.out.println(userService);
    }
}
