package com.sunny.maven.microservice.order.handler;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author SUNNY
 * @description: 处理Sentinel系统规则，返回自定义异常
 * @create: 2023-03-31 19:23
 */
@Component
public class MyUrlBlockHandler implements BlockExceptionHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e)
            throws Exception {
        String msg = null;
        if (e instanceof FlowException) {
            msg = "限流了";
        } else if (e instanceof DegradeException) {
            msg = "降级了";
        } else if (e instanceof ParamFlowException) {
            msg = "热点参数限流";
        } else if (e instanceof SystemBlockException) {
            msg = "系统规则（负载/...不满足要求）";
        } else if (e instanceof AuthorityException) {
            msg = "授权规则不通过";
        }
        // http状态码
        httpServletResponse.setStatus(500);
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setHeader("Content-Type", "application/json;charset=utf-8");
        httpServletResponse.setContentType("application/json;charset=utf-8");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 500);
        jsonObject.put("codeMsg", msg);
        httpServletResponse.getWriter().write(jsonObject.toJSONString());
    }
}
