package com.sunny.maven.security.token;

import com.sunny.maven.security.entity.Oauth2UserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author SUNNY
 * @description: 令牌增强器类
 * @create: 2022-02-22 16:59
 */
public class JwtTokenEnhancer implements TokenEnhancer {

    /**W
     * Enhance o auth 2 access token.
     * @param oAuth2AccessToken
     * @param oAuth2Authentication
     * @return
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        Map<String, Object> info = new HashMap<>(8);
        info.put("timestamp", String.valueOf(System.currentTimeMillis()));
        Authentication authentication = oAuth2Authentication.getUserAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            Object principal = authentication.getPrincipal();
            info.put("loginName", ((UserDetails) principal).getUsername());
            if (principal instanceof Oauth2UserDetails) {
                info.put("userId", ((Oauth2UserDetails) principal).getGuid());
            }
        }
        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(info);
        return oAuth2AccessToken;
    }
}
