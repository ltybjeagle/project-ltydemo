package com.sunny.maven.security;

import com.sunny.maven.core.common.constants.CommonConstant;
import com.sunny.maven.core.common.context.UserInfoContext;
import com.sunny.maven.core.common.context.UserInfoContextHolder;
import com.sunny.maven.core.common.domain.UserDto;
import com.sunny.maven.core.common.rstenum.ResultCodeEnum;
import com.sunny.maven.core.utils.crypto.Base64Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author SUNNY
 * @description: 授权 filter
 * @create: 2022-09-14 17:19
 */
@Slf4j
public class JwtTokenAuthenticationFilter extends BasicAuthenticationFilter {
    private Jackson2JsonRedisSerializer jackson2JsonRedisSerializer;
    public JwtTokenAuthenticationFilter(AuthenticationManager authenticationManager,
                                        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer) {
        super(authenticationManager);
        this.jackson2JsonRedisSerializer = jackson2JsonRedisSerializer;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        log.info("JwtTokenAuthenticationFilter过滤器,==================={}", request.getRequestURI());
        if (checkPrefixWhile(request.getRequestURI())) {
            chain.doFilter(request, response);
            return ;
        }
        String userInfo = request.getHeader("userInfo");
        if (StringUtils.hasText(userInfo)) {
            String userInfoJson = null;
            try {
                userInfoJson = new String(Base64Utils.decryBase64(userInfo));
            } catch (Exception ex) {
                throw new RuntimeException(ResultCodeEnum.USER_SESSION_LOSE.getCodeMsg());
            }
            log.info("会话信息userInfoJson：{}", userInfoJson);
            UserInfoContext userInfoContext =
                    (UserInfoContext) jackson2JsonRedisSerializer.deserialize(userInfoJson.getBytes());
//            UserDto userDto = userInfoContext.getUserDto();
//            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                    userDto.getUsername(), userInfoContext, userDto.getAuthorities());
            UserInfoContextHolder.setAuthentication(userInfoContext);
            chain.doFilter(request, response);
        } else {
            throw new RuntimeException(ResultCodeEnum.USER_SESSION_LOSE.getCodeMsg());
        }
    }

    /**
     * 白名单校验
     * @param requestUri
     * @return
     */
    private boolean checkPrefixWhile(String requestUri) {
        return CommonConstant.REQUEST_PREFIX_WHILE.stream().anyMatch(requestUri::contains);
    }
}
