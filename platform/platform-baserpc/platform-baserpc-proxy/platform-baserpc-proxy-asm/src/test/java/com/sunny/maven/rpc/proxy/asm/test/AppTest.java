package com.sunny.maven.rpc.proxy.asm.test;

import com.sunny.maven.rpc.proxy.asm.proxy.ASMProxy;
import com.sunny.maven.rpc.proxy.asm.user.UserService;
import com.sunny.maven.rpc.proxy.asm.user.impl.UserServiceInvocationHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author SUNNY
 * @description: 测试
 * @create: 2023-01-17 09:34
 */
@Slf4j
public class AppTest {

    @Test
    public void testAsm() throws Exception {
        log.info("ASM动态代理===========================");
        UserService userServiceAsm = (UserService) ASMProxy.newProxyInstance(AppTest.class.getClassLoader(),
                new Class[] {UserService.class}, new UserServiceInvocationHandler());
        log.info("" + userServiceAsm.getClass());
        log.info("" + userServiceAsm.login("admin", "admin"));
        log.info("" + userServiceAsm.login("admin", "admin1"));
    }
}
