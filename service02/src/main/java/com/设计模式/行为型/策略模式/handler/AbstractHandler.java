package com.设计模式.行为型.策略模式.handler;

import com.设计模式.行为型.策略模式.OrderDTO;

/**
 * @Auther: curry.zhang
 * @Date: 2019/9/20 19:02
 * @Description: 抽象处理器
 */
public abstract class AbstractHandler {
    abstract public String handle(OrderDTO orderDTO);
}
