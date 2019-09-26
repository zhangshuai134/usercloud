package com.设计模式.行为型.策略模式;

import java.lang.annotation.*;

/**
 * @Auther: curry.zhang
 * @Date: 2019/9/20 19:00
 * @Description:
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface HandlerType {
    String value();
}
