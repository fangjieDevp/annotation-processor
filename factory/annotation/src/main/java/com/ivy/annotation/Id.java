package com.ivy.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Function: com.ivy.annotation
 *
 * @author: fangjie07
 * @date: 2018/5/27
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface Id {

    String column();    //该id属性对应表中的列名
    String type();      //id属性类型
    String generator(); //使用的策略
}
