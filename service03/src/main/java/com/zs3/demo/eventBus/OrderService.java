package com.zs3.demo.eventBus;

import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;
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

    public static void main(String[] args) {
        int iterNum = 10000;

        // *) java 模式运行
        long beg = System.currentTimeMillis();
        for ( int j = 0; j < iterNum; j++ ) {
            int a = 0;
            for ( int i = 0; i < 10000; i++ ) {
                a = a + i;
            }
        }
        long end = System.currentTimeMillis();
        System.out.println(String.format("Java consume: %dms", end - beg));

        // *) Lua脚本解析执行
        String luaStr = "a = 0; for i = 0, 10000, 1 do a = a + i; end";
        Globals globals = JsePlatform.standardGlobals();
        LuaValue chunk = globals.load(luaStr);
        beg = System.currentTimeMillis();
        for ( int i = 0; i < iterNum; i++ ) {
            chunk.call();
        }
        end = System.currentTimeMillis();
        System.out.println(String.format("Lua consume: %dms", end - beg));

    }
    public void createOrder() throws InterruptedException {
        // 创建订单
        // 发送异步事件
        eventBusCenter.postAsync(new OrderCreatedEvent(1L, 1L));
        System.out.println("发送异步事件,订单创建");
        eventBusCenter.postAsync(new OrderChangeEvent(1L, 1L));
        System.out.println("发送异步事件,订单修改");

        //发送同步事件
//        Thread.sleep(500);
//        try {
//            System.out.println("发送同步事件,订单修改，开始");
//            eventBusCenter.postSync(new OrderChangeEvent(1L, 1L));
//            System.out.println("发送同步事件,订单修改，结束");
//        } catch (Exception e) {
//            log.error("发送同步事件,抓异常");
//        }
        eventBusCenter.postAsync(new Book(123));
        System.out.println("Book 发送");
    }
}
