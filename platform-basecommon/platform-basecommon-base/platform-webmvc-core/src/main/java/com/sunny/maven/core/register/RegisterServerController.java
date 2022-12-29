package com.sunny.maven.core.register;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @author SUNNY
 * @description: 服务注册接口
 * @create: 2022-08-17 14:41
 */
@Slf4j
@RestController
@RequestMapping(value = "/register")
public class RegisterServerController {
    /**
     * 配置监听接入点
     * @param request
     * @param response
     */
    @RequestMapping("/listener")
    public void addListener(HttpServletRequest request, HttpServletResponse response) {
        String serverName = request.getParameter("serverName");
        // 开启异步
        AsyncContext asyncContext = request.startAsync(request, response);
        AsyncTask asyncTask = new AsyncTask(asyncContext, true);

        // 维护 dataId 和异步请求上下文的关联
        dataIdContext.put(serverName, asyncTask);

        // 启动定时器，30s 后写入 304 响应
        timeOutCheck.schedule(() -> {
            if (asyncTask.isTimeOut()) {
                dataIdContext.remove(serverName, asyncTask);
                response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                asyncContext.complete();
            }
        }, 30000, TimeUnit.MILLISECONDS);
    }

    /**
     * 服务发布接入点
     * @param serverName 服务名
     * @param serverInfo 服务信息
     * @return
     */
    @RequestMapping("/publish")
    @SneakyThrows
    public String publishConfig(String serverName, String serverInfo) {
        log.info("publish server serverName: [{}], serverInfo: {}", serverName, serverInfo);
        Collection<AsyncTask> serverInfos = dataIdContext.get(serverName);
        serverInfos.forEach(asyncTask -> {
            asyncTask.setTimeOut(false);
            HttpServletResponse response = (HttpServletResponse) asyncTask.getAsyncContext().getResponse();
            response.setStatus(HttpServletResponse.SC_OK);
            try {
                response.getWriter().println(serverInfo);
            } catch (IOException e) {
                e.printStackTrace();
            }
            asyncTask.getAsyncContext().complete();
        });
        return "success";
    }

    /**
     * 提供的多值 Map，一个 key 可以对应多个 value
     */
    private Multimap<String, AsyncTask> dataIdContext = Multimaps.synchronizedMultimap(HashMultimap.create());
    private ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("longPolling-timeout-checker-%d")
            .build();
    private ScheduledExecutorService timeOutCheck = new ScheduledThreadPoolExecutor(1, threadFactory);

    @Data
    private static class AsyncTask {
        /**
         * 长轮询请求的上下文，包含请求和响应体
         */
        private AsyncContext asyncContext;
        /**
         * 超时标记
         */
        private boolean timeOut;

        /**
         * 构造函数
         * @param asyncContext
         * @param timeOut
         */
        public AsyncTask(AsyncContext asyncContext, boolean timeOut) {
            this.asyncContext = asyncContext;
            this.timeOut = timeOut;
        }
    }
}
