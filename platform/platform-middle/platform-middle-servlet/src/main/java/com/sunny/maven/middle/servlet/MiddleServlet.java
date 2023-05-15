package com.sunny.maven.middle.servlet;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author SUNNY
 * @description: MiddleServlet
 * @create: 2023-05-07 16:52
 */
@Slf4j
@WebServlet("/middle")
public class MiddleServlet extends HttpServlet {
    private static final long serialVersionUID = -658192815763458860L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*
         * 创建会话（不存在会话就会创建会话是根据请求中的sessionId来查的，sessionId在Cookie中
         * 创建的操作是：先创建出一个HttpSession对象（作为value），然后会自动生成一个随机的字符串，作为sessionId（作为Key），
         * 然后自动把key和value插入到hash表中,同时把这个生成的sessionId 通过 Set-Cookie 字段返回
         * 参数说明：true => 没有则创建；false => 没有不创建，返回null
         */
        HttpSession httpSession = req.getSession(true);
    }
}
