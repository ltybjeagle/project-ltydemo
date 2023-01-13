package com.sunny.maven.core.utils.web;

import com.sunny.maven.core.common.resp.R;
import com.sunny.maven.core.utils.json.JsonUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author SUNNY
 * @description: Response工具
 * @create: 2022-09-14 16:50
 */
public class ResponseUtils {
    /**
     * 输出响应结果
     * @param response
     * @param result
     * @param <T>
     */
    public static <T> void out(HttpServletResponse response, R<T> result) {
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter writer = response.getWriter()) {
            writer.write(JsonUtils.toJson(result));
            writer.flush();
        } catch (IOException e) {
        e.printStackTrace();
        }
    }
}
