package com.zs3.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RedisUtil {

    private static RedisTemplate redisTemplate = null;
    private static RedisUtil instance = null;

    public static RedisUtil getInstance(RedisTemplate redisTemplate){
        RedisUtil.redisTemplate = redisTemplate;
        if(instance == null){
            instance = new RedisUtil();
        }
        return instance;
    }

    public boolean setNx(String key, Object value, Integer timeout, TimeUnit timeUnit) {
        Boolean flag = redisTemplate.opsForValue().setIfAbsent(key, value);
        if (flag) {
            this.expire(key, timeout, timeUnit);
        }
        return flag;
    }

    public boolean setNx(String key, Object value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    public Boolean expire(String key, Integer timeout, TimeUnit timeUnit) {
        return redisTemplate.expire(key, timeout, timeUnit);
    }

    public void set(String key, Object obj, long time, String timeUnit) {
        TimeUnit unit = TimeUnit.valueOf(timeUnit);
        if (unit == null) {
            log.error("timeUnit is null return.");
            return;
        }
        redisTemplate.opsForValue().set(key, obj, time, unit);
    }

    public void set(String key, Object obj) {
        redisTemplate.opsForValue().set(key, obj);
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public void delete(Collection keys) {
        redisTemplate.delete(keys);
    }

    /**
     * 优先级高的
     */
    public void rPush(String key, String value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 保留 start 到 end 的数据，坐标从0开始
     * @param key
     * @param start
     * @param end
     */
    public void ltrim(String key, long start, long end) {
        redisTemplate.opsForList().trim(key, start, end);
    }

    public Long ttl(String key) {
        return redisTemplate.getExpire(key);
    }

    public Set hkeys(Object key) {
        return redisTemplate.opsForHash().keys(key);
    }

    /**
     * <p>
     * 通过key 对value进行加值+1操作,当value不是int类型时会返回错误,当key不存在是则value为1
     * </p>
     *
     * @param key
     * @return 加值后的结果
     */
    public Long incr(String key,long delta) {
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            redisTemplate.setValueSerializer(new StringRedisSerializer());
            return operations.increment(key, delta);
    }

    public Object hGet(Object key, Object field) {
        return redisTemplate.opsForHash().get(key, field);
    }

    public void hSet(Object key, Object field, Object value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    public Long getExpire(String key, TimeUnit timeUnit) {
        return redisTemplate.getExpire(key, timeUnit);
    }

    public void  persistKey(String key) {
        redisTemplate.persist(key);
    }
}

