package com.zs3.demo.eventBus;

import lombok.Data;

/**
 * @Auther: curry.zhang
 * @Date: 2019/9/11 10:18
 * @Description:
 */
@Data
public class OrderCreatedEvent {
    private long orderId;
    private long userId;
    public OrderCreatedEvent(long orderId, long userId) {
        this.setOrderId(orderId);
        this.setUserId(userId);
    }
}
