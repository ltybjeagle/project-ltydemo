package com.sunny.maven.servlet;

import com.sunny.maven.facade.HelloFacade;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author SUNNY
 * @description: 请求Servlet
 * @create: 2021-09-04 11:01
 */
@WebServlet(description = "hello servlet", urlPatterns = "/servlet/hello", loadOnStartup = 1)
public class HelloServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(HelloServlet.class);
    /**
     * 逻辑对象
     */
    private HelloFacade helloFacade;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter printWriter = resp.getWriter();
        String name = req.getParameter("name");
        logger.info("进入HelloServlet，参数：{}", name);
        try {
            String result = this.helloFacade.sayHello(name);
            logger.info("HelloServlet返回结果，结果：{}", result);
            printWriter.write(result);
        } catch (Exception e) {
            logger.info("HelloServlet异常，{}", e.getMessage());
            String errMg = e.getMessage();
            errMg = errMg.substring(StringUtils.indexOf(errMg, "{message=") + 9, errMg.length() - 1);
            //printWriter.write(errMg);
            throw new ServletException(errMg);
        }
    }

    public HelloServlet(@Autowired HelloFacade helloFacade) {
        this.helloFacade = helloFacade;
    }
}
