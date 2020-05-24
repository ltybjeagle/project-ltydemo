package com.sunny.maven.pagecore.servlet;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/5/10 18:27
 * @description：前端服务前置servlet
 * @modified By：
 * @version: 1.0.0
 */
@Slf4j
public class PlatFormPageServlet extends HttpServlet {
    private static final String ERROR_PAGE = "/exception/404.jsp";
    private ServletConfig config = null;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.config = config;
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/plain;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try {
            platformServer(request, response);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    /**
     * 前端请求处理
     * @param req
     * @param resp
     * @throws Exception
     */
    private void platformServer(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        log.info("PlatFormPageServlet...");
        resp.setHeader("Pragma", "No-cache");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setDateHeader("Expires", -10);
        // 设置返回内容文档格式
        resp.setContentType("text/html;application/xhtml+xml;charset=UTF-8");

        String requestURI = req.getRequestURI();
        String exName = "";
        if (!"/error".equalsIgnoreCase(requestURI)) {
            exName = requestURI.substring(requestURI.lastIndexOf("."));
        }
        if (".page".equalsIgnoreCase(exName)) {
            log.info("page request...");
            pageServer(requestURI, req, resp);
            return;
        } else if (".rcp".equalsIgnoreCase(exName)) {
            log.info("ajax request...");
        } else {
            log.info("error request...");
            resp.setCharacterEncoding("UTF-8");
            req.getRequestDispatcher(ERROR_PAGE).forward(req, resp);
            return;
        }

    }

    /**
     * Page请求处理
     * @param requestURI
     * @param request
     * @param response
     */
    private void pageServer(String requestURI, HttpServletRequest request, HttpServletResponse response) {
        String pageid = requestURI.substring(request.getContextPath().length(), requestURI.lastIndexOf(".page"));
    }

    @Override
    public ServletConfig getServletConfig() {
        return config;
    }
}
