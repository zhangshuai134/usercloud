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
@HandlerType("1")
public class HandlerImpl01 extends AbstractHandler {

    @Override
    public String handle(OrderDTO orderDTO) {
        System.out.println("处理type为1的订单,orderDTO.type="+orderDTO.getType());
        return "success";
    }
}
