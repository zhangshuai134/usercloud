package com.zs.demo;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Semaphore;

/**
 * @Auther: curry.zhang
 * @Date: 2019/9/12 10:55
 * @Description:
 */
@RestController
@Slf4j
public class RateLimiterController {

    RateLimiter rateLimiter = RateLimiter.create(5.0);

    @RequestMapping("/test")
    public String test(){
        if (rateLimiter.tryAcquire()) {
            log.info("success");
            return "success";
        }
        log.info("false");
        return "false";
    }
    public static void main(String[] args) {
        String start = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        RateLimiter limiter = RateLimiter.create(2.0); // 这里的2表示每秒允许处理的量为2个
        for (int i = 1; i <= 10; i++) {
            limiter.acquire();// 请求RateLimiter, 超过permits会被阻塞
            log.info("执行。。。" + i);
        }
        String end = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        System.out.println("start time:" + start);
        System.out.println("end time:" + end);


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
