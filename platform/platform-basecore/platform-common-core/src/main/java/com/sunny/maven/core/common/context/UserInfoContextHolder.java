package com.sunny.maven.core.common.context;

import com.sunny.maven.core.common.constants.CommonConstant;
import org.springframework.util.Assert;

import java.util.Collection;

/**
 * @author SUNNY
 * @description: 用户会话上下文存储（绑定线程）
 * @create: 2022-09-21 14:34
 */
public class UserInfoContextHolder {
    public static final UserInfoContext getAuthentication() {
        UserInfoContext context = (UserInfoContext) ThreadLocalMap.get(CommonConstant.USER_CONTEXT);
        if (context == null) {
            context = createEmptyContext();
            ThreadLocalMap.put(CommonConstant.USER_CONTEXT, context);
        }
        return (UserInfoContext) ThreadLocalMap.get(CommonConstant.USER_CONTEXT);
    }

    public static final Collection<String> getAuthorities() {
        UserInfoContext context = getAuthentication();
        return context.getUserDto().getAuthorities();
    }

    public static final void setAuthentication(UserInfoContext context) {
        Assert.notNull(context, "Only non-null UserInfoContext instances are permitted");
        ThreadLocalMap.put(CommonConstant.USER_CONTEXT, context);
    }

    public static final UserInfoContext createEmptyContext(){
        return new UserInfoContext();
    }
}
