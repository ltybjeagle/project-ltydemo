package com.sunny.maven.core.extend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.SmartInitializingSingleton;

/**
 * @author SUNNY
 * @description: SmartInitializingSingleton 在spring容器管理的所有单例对象（非懒加载对象）初始化完成之后调用的回调接口
 *               用户可以扩展此接口在对所有单例对象初始化完毕后，做一些后置的业务处理
 * @create: 2022-10-18 16:35
 */
@Slf4j
public class ExtSmartInitializingSingleton implements SmartInitializingSingleton {
    @Override
    public void afterSingletonsInstantiated() {
        log.debug("19.[SmartInitializingSingleton] afterSingletonsInstantiated");
    }
}
