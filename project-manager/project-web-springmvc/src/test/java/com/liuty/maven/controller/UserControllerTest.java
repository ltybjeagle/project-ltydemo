package com.liuty.maven.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 用户管理测试类
 */
public class UserControllerTest {

    private MockMvc mockMvc;

    /**
     * 初始化
     */
    @Before
    public void setup() {
        UserController userController = new UserController();
        MockitoAnnotations.initMocks(this);
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        mockMvc = standaloneSetup(userController).setViewResolvers(resolver).build();
    }

    /**
     * 测试SpringMvc listUser方法
     */
    @Test
    public void listUserTest() throws Exception {
        mockMvc.perform(get("/user/listuser"))
                .andExpect(view().name("listuser"));
    }

    /**
     * 测试SpringMvc getUser方法
     * @throws Exception
     */
    @Test
    public void getUserTest() throws Exception{
        mockMvc.perform(get("/user/getuser/123456789"))
                .andExpect(view().name("oneuser"));
    }
}
