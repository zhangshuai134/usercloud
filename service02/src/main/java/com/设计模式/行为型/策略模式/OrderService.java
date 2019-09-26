package com.设计模式.行为型.策略模式;

/**
 * @Auther: curry.zhang
 * @Date: 2019/9/20 18:56
 * @Description:
 */
public interface OrderService {
    /**
     * 根据订单类型处理订单
     * @param orderDTO
     * @return
     */
    String handle(OrderDTO orderDTO);
}
