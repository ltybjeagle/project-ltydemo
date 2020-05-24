package com.sunny.maven.page.index;

import com.sunny.maven.pagecore.annotation.PageConsole;
import com.sunny.maven.pagecore.annotation.PageTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/5/24 16:00
 * @description：Index界面
 * @modified By：
 * @version: 1.0.0
 */
@Component
public class PageIndex {

    @PageConsole(
            appid = "common",
            url = "/busfw/template/show/index",
            pageTempltate = @PageTemplate(funcShow = "Ext.ui.template"),
            page = "/busfw/template/template_defautl.jsp",
            config = "{}"
//            component = {
//                    @PageComponent(serviceid = "btntools", id = "btntools", config = "{}")
//            }
    )
    public Map<String, Object> index(HttpServletRequest request, HttpServletResponse response, Map config) {
        return config;
    }
}
