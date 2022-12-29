package com.sunny.maven.core.servlet;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author SUNNY
 * @description: 同步Servlet
 * @create: 2022-09-28 16:02
 */
@Slf4j
@WebServlet(name = "SyncServlet", urlPatterns = "/test/SyncServlet")
public class SyncServlet extends HttpServlet {
    private static final long serialVersionUID = 2891950305677670168L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long start = System.currentTimeMillis();
        doSomeTing(req, resp);
        log.info("Sync use : {}", (System.currentTimeMillis() - start));
    }

    private void doSomeTing(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        resp.getWriter().append("done");
    }
}
