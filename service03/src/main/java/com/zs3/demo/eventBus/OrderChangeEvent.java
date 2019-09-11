package com.zs3.demo.eventBus;

import lombok.Data;

/**
 * @Auther: curry.zhang
 * @Date: 2019/9/11 10:42
 * @Description:
 */
@Data
public class OrderChangeEvent {
    private long orderId;
    private long userId;
    public OrderChangeEvent(long orderId, long userId) {
        this.setOrderId(orderId);
        this.setUserId(userId);
    }
}
