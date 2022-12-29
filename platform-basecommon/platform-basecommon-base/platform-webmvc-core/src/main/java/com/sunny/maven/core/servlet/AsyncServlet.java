package com.sunny.maven.core.servlet;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author SUNNY
 * @description: 异步Servlet
 * @create: 2022-09-28 16:15
 */
@Slf4j
@WebServlet(name = "AsyncServlet", urlPatterns = "/test/AsyncServlet", asyncSupported = true)
public class AsyncServlet extends HttpServlet {
    private static final long serialVersionUID = 2090002388663353494L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long start = System.currentTimeMillis();
        // 1、开启异步
        AsyncContext asyncContext = req.startAsync();
        // 2、要执行的代码放到一个独立的线程中，多线程/线程池
        CompletableFuture.runAsync(() -> {
            try {
                doSomeTing(asyncContext, asyncContext.getRequest(), asyncContext.getResponse());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        log.info("Async use : {}", (System.currentTimeMillis() - start));
    }

    private void doSomeTing(AsyncContext asyncContext, ServletRequest req, ServletResponse resp)
            throws IOException {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        resp.getWriter().append("async done");
        // 3、业务代码处理完毕，通知结束
        asyncContext.complete();
    }
}
