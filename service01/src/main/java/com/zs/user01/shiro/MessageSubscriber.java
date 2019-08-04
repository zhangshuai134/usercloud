package com.zs.user01.shiro;

import com.zs.user01.service.ShiroService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 消息监听
 */
@Slf4j
public class MessageSubscriber implements MessageListener {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ShiroService shiroService;

    @Override
    public void onMessage(Message message, byte[] bytes) {
        Object value = redisTemplate.getValueSerializer().deserialize(message.getBody());
        log.info("topic message: {}" , String.valueOf(value));
        shiroService.updatePermission();
    }
}
