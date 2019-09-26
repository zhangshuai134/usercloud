package com.设计模式.行为型.策略模式.handler;

import org.springframework.stereotype.Component;
import com.设计模式.行为型.策略模式.HandlerType;
import com.设计模式.行为型.策略模式.OrderDTO;

/**
 * @Auther: curry.zhang
 * @Date: 2019/9/20 19:03
 * @Description:
 */
@Component
@HandlerType("3")
public class HandlerImpl03 extends AbstractHandler {
    
    @Override
    public String handle(OrderDTO orderDTO) {
        System.out.println("处理type为3的订单,orderDTO.type="+orderDTO.getType());
        return "success";
    }
}
