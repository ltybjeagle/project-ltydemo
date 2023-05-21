package com.sunny.maven.core.annotation.database.encryption;

import java.lang.annotation.*;

/**
 * @author SUNNY
 * @description: 该注解定义在类上
 *               插件通过扫描类对象是否包含这个注解来决定是否继续扫描其中的字段注解
 *               这个注解要配合EncryptTransaction注解
 * @create: 2023-05-11 17:19
 */
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SensitiveData {
}
