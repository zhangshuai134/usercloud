package com.zs3.demo;

import com.zs3.demo.eventBus.OrderService;
import com.zs3.demo.lua.TestLuaJ;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Auther: curry.zhang
 * @Date: 2019/9/11 10:28
 * @Description:
 */
@Component
@Slf4j
@Order(1)
public class FistRun implements CommandLineRunner {

    @Autowired
    private OrderService orderService;
    @Autowired
    private TestLuaJ testLuaJ;

    @Override
    public void run(String... args) throws Exception {
        log.info("FistRun start===============");
//        orderService.createOrder();

        testLuaJ.set();
        testLuaJ.get();
        testLuaJ.redisAddScriptExec();
    }
}
