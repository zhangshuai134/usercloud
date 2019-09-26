package com.设计模式.行为型.策略模式;

import com.设计模式.行为型.策略模式.handler.AbstractHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Auther: curry.zhang
 * @Date: 2019/9/20 19:45
 * @Description:
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private HandlerContext handlerContext;

    @Override
    public String handle(OrderDTO orderDTO) {
        System.out.println("OrderServiceImpl handle 方法开始执行===");
        AbstractHandler handler = handlerContext.getInstance(orderDTO.getType());
        return handler.handle(orderDTO);
    }
}
