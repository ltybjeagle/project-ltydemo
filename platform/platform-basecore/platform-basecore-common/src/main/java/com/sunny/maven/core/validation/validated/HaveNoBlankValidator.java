package com.sunny.maven.core.validation.validated;

import com.sunny.maven.core.validation.HaveNoBlank;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author SUNNY
 * @description: 字符串空格校验处理类
 * @create: 2023/6/28 18:35
 */
public class HaveNoBlankValidator implements ConstraintValidator<HaveNoBlank, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // null不做校验
        if (null == value) return true;
        return !value.contains(" ");
    }
}
