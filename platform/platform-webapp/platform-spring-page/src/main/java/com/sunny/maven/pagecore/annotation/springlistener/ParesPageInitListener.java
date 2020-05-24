package com.sunny.maven.pagecore.annotation.springlistener;

import org.springframework.beans.factory.parsing.ComponentDefinition;
import org.springframework.beans.factory.parsing.EmptyReaderEventListener;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/5/24 16:44
 * @description：Page注解解析监听器
 * @modified By：
 * @version: 1.0.0
 */
public class ParesPageInitListener extends EmptyReaderEventListener {
    @Override
    public void componentRegistered(ComponentDefinition componentDefinition) {

    }
}
