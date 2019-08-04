package com.zs.user01.shiro;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RedisPubMsgService {

    private static  final  String TOPIC = "/redis/permission";

    private static  final  String MSG = "update permission";

    @Autowired
    private RedisTemplate redisTemplate;

    public void pubMessage() {
        log.info("update permission");
        redisTemplate.convertAndSend(TOPIC, MSG);
    }

}
