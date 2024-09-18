package com.easywork.annotation;

import com.easywork.enums.VerifyRegexEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface VerifyParam {

    VerifyRegexEnum regex() default VerifyRegexEnum.NO;
    //最小长度
    int min() default -1;
    //最大长度
    int max() default -1;

    boolean required() default false;
}
