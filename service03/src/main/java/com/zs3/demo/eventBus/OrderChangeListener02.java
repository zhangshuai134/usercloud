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
public class OrderChangeListener02 {

    @Subscribe
    public void created(OrderCreatedEvent event) {
        long orderId = event.getOrderId();
        long userId = event.getUserId();
        // 订单创建成功后的各种操作，如发短信、发邮件等等。
        // 注意，事件可以被订阅多次，也就是说可以有很多方法监听 OrderCreatedEvent 事件，
        // 所以没必要在一个方法中处理发短信、发邮件、更新库存等
        log.info("订单创建监听02，修改库存，orderId=" + orderId);
    }

}
