package com.niu.web.business.annotationUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author niushuanglong
 * @date 2022/12/8 21:14:44
 * @description 操作日志注解
 */
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OperationLog {

    String defaultName() default "无操作";

    String logIp() default  "";

    String operationType() default "";

    String value();

    enum SYSCODE{
        IN
    }
}
