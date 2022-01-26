package com.sunny.maven.core.security.filter;

import com.sunny.maven.core.common.constant.CommonConstant;
import com.sunny.maven.core.common.context.UserInfoContextHolder;
import com.sunny.maven.core.common.enums.ReturnEnum;
import com.sunny.maven.core.exception.AppException;
import com.sunny.maven.core.security.auth.SunnyAuthClientPO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author SUNNY
 * @description: 认证校验过滤器
 * @create: 2022-01-14 16:29
 */
@Component
@Order(1)
@WebFilter(filterName = "AuthFilter", urlPatterns = "/*")
public class AuthenticationTokenFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationTokenFilter.class);

    /**
     * 认证服务对象
     */
    private SunnyAuthClientPO sunnyAuthClientPO;
    /**
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        logger.info("AuthenticationTokenFilter......");
        try {
            String token_id = this.getTokenId(httpServletRequest);
            Map<String, Object> loginInfo = sunnyAuthClientPO.getLoginInfo(token_id);
            Map<String, Object> userDTO = (Map<String, Object>) loginInfo.get("userDTO");
            String province = (String) loginInfo.get("province");
            UserInfoContextHolder.getContext().setTokenId(token_id);
            UserInfoContextHolder.getContext().setUserDto(userDTO);
            UserInfoContextHolder.getContext().setDatasourceKey(province);
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            UserInfoContextHolder.delContext();
        } catch (AppException e) {
            httpServletRequest.setAttribute("filter.error", e);
            httpServletRequest.getRequestDispatcher("/error/throw").forward(httpServletRequest, httpServletResponse);
        }
    }

    /**
     * 从request获取tokenid
     * @param request 请求对象
     * @return String
     */
    protected String getTokenId(HttpServletRequest request) {
        String token_id = request.getHeader(CommonConstant.TOKEN_ID);
        if (StringUtils.isEmpty(token_id)) {
            token_id = request.getParameter(CommonConstant.TOKEN_ID);
        }
        if (StringUtils.isEmpty(token_id)) {
            throw new AppException.AppExceptionBuilder().code(ReturnEnum.UAC501.getMsgCode()).build();
        }
        return token_id;
    }

    /**
     * 构造函数
     * @param sunnyAuthClientPO 认证服务
     */
    @Autowired
    public AuthenticationTokenFilter(SunnyAuthClientPO sunnyAuthClientPO) {
        this.sunnyAuthClientPO = sunnyAuthClientPO;
    }
}
