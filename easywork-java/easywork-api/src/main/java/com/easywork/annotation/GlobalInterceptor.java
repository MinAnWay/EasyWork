package com.easywork.annotation;

import com.easywork.enums.RequestFrequencyTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GlobalInterceptor {
    /**
     * 校验登录
     */
    boolean checkLogin() default false;

    /**
     * 校验参数
     */
    boolean checkParams() default true;

    int requestFrequencyThreshold() default 0;

    RequestFrequencyTypeEnum frequencyType() default RequestFrequencyTypeEnum.NO_LIMIT;
}
