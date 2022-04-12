package com.sunny.maven.security.provider;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author SUNNY
 * @description: 认证补充信息
 * @create: 2022-02-09 14:20
 */
public class Oauth2WebAuthenticationDetails extends WebAuthenticationDetails {

    /**
     * 构造函数
     * 补充验证码信息
     * @param request 请求
     */
    public Oauth2WebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        String requestCode = request.getParameter("captcha");
        HttpSession session = request.getSession();
        String savedCode = (String) session.getAttribute("captcha");
        if (StringUtils.isNotEmpty(savedCode)) {
            // 清楚验证码，无论成功还是失败，客户端都刷新验证码
            session.removeAttribute("captcha");
            // 验证码正确时，设置验证状态
            if (StringUtils.isNotEmpty(requestCode) && StringUtils.equals(requestCode, savedCode)) {
                this.imageCodeIsRight = true;
            }
        }
    }

    /**
     * 验证码校验
     */
    private boolean imageCodeIsRight;

    public boolean isImageCodeIsRight() {
        return this.imageCodeIsRight;
    }
}
