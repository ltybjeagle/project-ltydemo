package com.liuty.maven.test;

import com.liuty.maven.MainApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * 类注解说明：
 *      1、注解@RunWith(SpringJUnit4ClassRunner.class)：引入Spring对JUnit4的支持
 *      2、注解@SpringBootTest：Spring Boot单元测试类，关联启动类
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {DropwizardMetricsMBeansAutoConfiguration.class})
//@SpringBootTest(classes = MainApplication.class)
public class UserApplicationTest {

    private static final Logger logger = LoggerFactory.getLogger(UserApplicationTest.class);

    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;

    //@Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    //@Test
    public void findByIdTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/userRest/user/findbyid/1")
                        .accept(MediaType.APPLICATION_JSON)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();
        logger.info("请求状态：{}, 请求内容：{}", status, content);
    }
}
