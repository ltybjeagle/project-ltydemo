package com.sunny.maven.core.common.context;

import com.sunny.maven.core.common.constant.CommonConstant;
import com.sunny.maven.core.util.ThreadLocalMap;
import org.springframework.util.Assert;

/**
 * @author SUNNY
 * @description: 用户会话上下文存储（绑定线程）
 * @create: 2022-01-17 12:19
 */
public class UserInfoContextHolder {
    public static final UserInfoContext getContext() {
        UserInfoContext context = (UserInfoContext) ThreadLocalMap.get(CommonConstant.USER_CONTEXT);
        if (context == null) {
            context = createEmptyContext();
            ThreadLocalMap.put(CommonConstant.USER_CONTEXT, context);
        }
        return (UserInfoContext) ThreadLocalMap.get(CommonConstant.USER_CONTEXT);
    }

    public static final void setContext(UserInfoContext context) {
        Assert.notNull(context, "Only non-null UserContext instances are permitted");
        ThreadLocalMap.put(CommonConstant.USER_CONTEXT, context);
    }

    public static final void delContext() {
        ThreadLocalMap.remove(CommonConstant.USER_CONTEXT);
    }

    public static final UserInfoContext createEmptyContext(){
        return new UserInfoContext();
    }
}
