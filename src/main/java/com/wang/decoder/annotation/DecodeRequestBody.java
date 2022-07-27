package com.wang.decoder.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记该注解在方法上，会对请求参数做统一参数处理
 *
 * @author Wang
 * @since 2022/7/23
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface DecodeRequestBody {
    /**
     * 需要解码的字段
     */
    String[] value() default {};
}
