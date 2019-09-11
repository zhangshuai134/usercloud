package com.zs3.demo.eventBus;

import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Auther: curry.zhang
 * @Date: 2019/9/11 10:19
 * @Description:
 */
@Component
@EventBusListener
@Slf4j
public class OrderChangeListener {

    @Subscribe
    public void created(OrderCreatedEvent event) throws InterruptedException {
        long orderId = event.getOrderId();
        Thread.sleep(300);
        log.info("订单创建监听,发送短信，orderId=" + orderId);
    }

    @Subscribe
    public void change(OrderChangeEvent event) throws InterruptedException {
        long orderId = event.getOrderId();
        Thread.sleep(200);
        log.info("订单修改监听，物流变化，orderId=" + orderId);
    }
}
