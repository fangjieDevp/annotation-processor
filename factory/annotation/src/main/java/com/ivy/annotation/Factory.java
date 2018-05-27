package com.ivy.annotation;

/**
 * Function: com.ivy.annotation
 *
 * @author: ivy
 * @date: 2018/5/27
 */
public @interface Factory {

    /**
     * 工厂类型
     *
     * @return
     */
    Class<?> type();

    /**
     * 工厂的唯一标识
     *
     * @return
     */
    String id();
}
