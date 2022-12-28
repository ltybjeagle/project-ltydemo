package com.sunny.maven.springframework.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author SUNNY
 * @description: 资源处理接口
 * @create: 2022-12-16 10:18
 */
public interface Resource {
    /**
     * 获取输入流
     * @return
     * @throws IOException
     */
    InputStream getInputStream() throws IOException;
}
