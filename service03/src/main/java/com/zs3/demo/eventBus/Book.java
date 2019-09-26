package com.zs3.demo.eventBus;

import lombok.Data;

/**
 * @Auther: curry.zhang
 * @Date: 2019/9/11 14:43
 * @Description:
 */
@Data
public class Book {
    private int id;

    public Book(int id) {
        this.id = id;
    }
}
