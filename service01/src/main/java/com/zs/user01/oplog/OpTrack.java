package com.zs.user01.oplog;

import java.lang.annotation.*;

/**
 * @Auther: curry.zhang
 * @Date: 2019/6/27 16:36
 * @Description:
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
/**
 * 操作追踪
 */
public @interface OpTrack {

    /** 日志描述 */
    String description() default "默认默认默认默认默认默认";

    /** 模块名字 */
    String moduleName() default "td-server";

}
