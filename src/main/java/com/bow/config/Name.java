package com.bow.config;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 指明该组件的名字. <br/>
 * API {@link com.bow.common.ExtensionLoader#getExtension(String)} 根据名字找到该组件
 *
 * @author vv
 * @since 2016/10/7.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface Name {
    String value();
}
