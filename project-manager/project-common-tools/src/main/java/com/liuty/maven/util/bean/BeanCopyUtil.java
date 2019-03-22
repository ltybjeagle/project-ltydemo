package com.liuty.maven.util.bean;

import net.sf.cglib.beans.BeanCopier;

/**
 * 使用cglib的BeanCopier实现对象复制
 * @param <S>
 * @param <D>
 */
public class BeanCopyUtil<S, D> {
    private BeanCopier bc = null;

    public BeanCopyUtil(Class<S> src, Class<D> desc) {
        bc = BeanCopier.create(src, desc, false);
    }

    public <D> D getCopyObject(S s, D d) {
        bc.copy(s, d, null);
        return d;
    }
}
