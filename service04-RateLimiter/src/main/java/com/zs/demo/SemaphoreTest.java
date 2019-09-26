package com.zs.demo;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;

/**
 * @Auther: curry.zhang
 * @Date: 2019/9/12 17:56
 * @Description:
 */
@Slf4j
public class SemaphoreTest {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(2);
        for(int i=0;i<10;i++) {
            new Thread(()->{
                try {
                    semaphore.acquire();
                    log.info(Thread.currentThread().getName()+"执行");
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    semaphore.release();
                }
            },"线程"+i).start();
        }
    }
}
