package com.sunny.maven.pagecore.component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/5/24 17:00
 * @description：组件服务接口，定义了组件服务的标准接口
 * @modified By：
 * @version: 1.0.0
 */
public interface ComponentService {

    /**
     * 操作台加载时框架调用该方法，产生组件初始化配置
     * @param request
     * @param response
     * @param config 组件默认配置
     * @return
     * @throws Exception
     */
    Map<String, Object> loadComponent(HttpServletRequest request, HttpServletResponse response,
                                             Map<String, Object> config) throws Exception;
}
