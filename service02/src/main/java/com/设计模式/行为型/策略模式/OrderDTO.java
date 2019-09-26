package com.设计模式.行为型.策略模式;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Auther: curry.zhang
 * @Date: 2019/9/20 18:53
 * @Description:
 */
@Data
public class OrderDTO {
    private String code;
    private BigDecimal price;
    /**
     * 订单类型
     * 1：普通订单
     * 2：满减订单
     * 3：满返订单
     */
    private String type;
}
