package com.sunny.maven.microservice.middle.druid.api;

import com.alibaba.druid.stat.DruidStatManagerFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SUNNY
 * @description: DruidStatController
 * @create: 2023-04-06 21:58
 */
@RestController
@RequestMapping(value = "/druid")
public class DruidStatController {
    @GetMapping("/stat")
    public Object druidStat() {
        // 获取数据源的监控数据
        return DruidStatManagerFacade.getInstance().getDataSourceStatDataList();
    }
}
