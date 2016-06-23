package com.better517na.JavaServiceRouteHelper.thrift.log;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @切入点注解
 * @author guangyin
 *
 */
@Target({ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented  
@Inherited
public @interface ThriftServerACCLog {
    /**
     * @序号
     * @return
     */
    String seq() default "";

    /**
     * @接口描述
     * @return
     */
    String description() default "";
}
