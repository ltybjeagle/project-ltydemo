package com.sunny.maven.core.extend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;

/**
 * @author SUNNY
 * @description: DisposableBean 当此对象销毁时，会自动执行这个方法
 *               比如说运行applicationContext.registerShutdownHook时，就会触发这个方法
 * @create: 2022-10-18 16:42
 */
@Slf4j
public class ExtDisposableBean implements DisposableBean {
    @Override
    public void destroy() throws Exception {
        log.debug("21.[DisposableBean] destroy");
    }
}
