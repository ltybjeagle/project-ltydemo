package com.sunny.maven.core.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author SUNNY
 * @description: ServletContext监听器
 * @create: 2022-11-16 16:01
 */
@WebListener
public class GracefulServletContextListener implements ServletContextListener {
    private ServletContext sc;
    /**
     * Application被初始化的时候创建
     * @param sce
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // 创建一个Map，key为IP，value为该IP上所发出的会话的对象
        Map<String, List<HttpSession>> map = new HashMap<>(1024);
        sc = sce.getServletContext();
        // 将map放到全局域中
        sc.setAttribute("sessionMap", map);
    }
}
