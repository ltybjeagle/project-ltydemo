package com.sunny.maven.core.validation;

import com.sunny.maven.core.validation.validated.HaveNoBlankValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotBlank;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * @author SUNNY
 * @description: 字符串空格校验
 * @create: 2023/6/28 17:37
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {HaveNoBlankValidator.class}) // 标明由哪个类执行校验逻辑
public @interface HaveNoBlank {
    /**
     * 校验出错时默认返回的消息
     * @return
     */
    String message() default "字符串中不能含有空格";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    /**
     * 同一个元素上指定多个该注解时使用
     */
    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface List {
        NotBlank[] value();
    }
}
