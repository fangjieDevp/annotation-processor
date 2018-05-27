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
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Property {
    String column();    //该属性对应表中的列名
    String type();      //id属性类型
}


