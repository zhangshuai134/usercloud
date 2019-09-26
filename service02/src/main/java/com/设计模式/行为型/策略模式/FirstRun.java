package com.设计模式.行为型.策略模式;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @Auther: curry.zhang
 * @Date: 2019/9/23 10:11
 * @Description:
 */
@Component
@Slf4j
@Order(1)
public class FirstRun implements CommandLineRunner {
    @Autowired
    private OrderService orderService;

    @Override
    public void run(String... args) throws Exception {
        OrderDTO orderDTO01 = new OrderDTO();
        orderDTO01.setCode("code01");
        orderDTO01.setPrice(new BigDecimal(11));
        orderDTO01.setType("1");
        orderService.handle(orderDTO01);
        OrderDTO orderDTO02 = new OrderDTO();
        orderDTO02.setCode("code02");
        orderDTO02.setPrice(new BigDecimal(12));
        orderDTO02.setType("2");
        orderService.handle(orderDTO02);
        OrderDTO orderDTO03 = new OrderDTO();
        orderDTO03.setCode("code03");
        orderDTO03.setPrice(new BigDecimal(13));
        orderDTO03.setType("3");
        orderService.handle(orderDTO03);
    }
}
