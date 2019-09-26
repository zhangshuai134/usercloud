package com.zs.demo;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;

/**
 * @Auther: curry.zhang
 * @Date: 2019/9/12 17:55
 * @Description:
 */
@Slf4j
public class RateLimiterTest {
    public static void main(String[] args) {
        RateLimiter limiter = RateLimiter.create(2.0); // 这里的2表示每秒允许处理的量为2个
        for (int i = 1; i <= 10; i++) {
            limiter.acquire();// 请求RateLimiter, 超过permits会被阻塞
            log.info("执行。。。" + i);
        }
    }
}
