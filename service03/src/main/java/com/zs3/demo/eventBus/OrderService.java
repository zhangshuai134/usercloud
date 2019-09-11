package com.zs3.demo.eventBus;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: curry.zhang
 * @Date: 2019/9/11 10:20
 * @Description:
 */
@Service
@Slf4j
public class OrderService {

    @Autowired
    private EventBusCenter eventBusCenter;

    public void createOrder() throws InterruptedException {
        // 创建订单
        // 发送异步事件
        eventBusCenter.postAsync(new OrderCreatedEvent(1L, 1L));
        System.out.println("发送异步事件,订单创建");
        eventBusCenter.postAsync(new OrderChangeEvent(1L, 1L));
        System.out.println("发送异步事件,订单修改");

        //发送同步事件
        Thread.sleep(500);
        try {
            System.out.println("发送同步事件,订单修改，开始");
            eventBusCenter.postSync(new OrderChangeEvent(1L, 1L));
            System.out.println("发送同步事件,订单修改，结束");
        } catch (Exception e) {
            log.error("发送同步事件,抓异常");
        }

    }
}
