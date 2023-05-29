package com.sunny.maven.core.annotation.database.encryption;

import java.lang.annotation.*;

/**
 * @author SUNNY
 * @description: 该注解有两种使用方式
 *               ①：配合@SensitiveData加在类中的字段上
 *               ②：直接在Mapper中的方法参数上使用
 * @create: 2023-05-11 17:30
 */
@Documented
@Inherited
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface EncryptTransaction {
}
