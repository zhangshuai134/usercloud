package com.zs.user01.shiro;

import com.zs.user01.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
public class CustomerRedisCache<K, V> implements Cache<K, V> {

    @Autowired
    private RedisTemplate redisTemplate;


    private final String keyPrefix="shiro_redis_session:";

    private RedisUtil getRedisUtilInstance() {
        return RedisUtil.getInstance(redisTemplate);
    }

    private String getByteKey(K key) {
        if (key instanceof String) {
            String preKey = this.keyPrefix + key;
            return preKey;
        } else{
            return "";
        }
    }

    @Override
    public V get(K key) throws CacheException {
        log.debug("根据key从Redis中获取对象 key [" + key + "]");
        try {
            if (key == null) {
                return null;
            } else {
                Object value=getRedisUtilInstance().get(this.getByteKey(key));
                return (V)value;
            }
        } catch (Throwable var4) {
            throw new CacheException(var4);
        }
    }

    @Override
    public V put(K key, V value) throws CacheException {
        log.debug("根据key从存储 key [" + key + "]");

        try {
            getRedisUtilInstance().set(this.getByteKey(key), value);
            return value;
        } catch (Throwable var4) {
            throw new CacheException(var4);
        }
    }

    @Override
    public V remove(K key) throws CacheException {
        log.debug("从redis中删除 key [" + key + "]");

        try {
            V previous = this.get(key);
            getRedisUtilInstance().delete(this.getByteKey(key));
            return previous;
        } catch (Throwable var3) {
            throw new CacheException(var3);
        }
    }

    @Override
    public void clear() throws CacheException {
        log.debug("从redis中删除所有元素,安全期间不实现此方法");
    }

    @Override
    public int size() {
        //不实现此方法
        return 0;
    }

    @Override
    public Set<K> keys() {
//        try {
//            Set<K> keys = getRedisUtilInstance().hkeys(this.keyPrefix + "*");
//            if (CollectionUtils.isEmpty(keys)) {
//                return Collections.emptySet();
//            } else {
//                Set<K> newKeys = new HashSet();
//                Iterator ikeys = keys.iterator();
//
//                while(ikeys.hasNext()) {
//                    K key = (K) ikeys.next();
//                    newKeys.add(key);
//                }
//
//                return newKeys;
//            }
//        } catch (Throwable var5) {
//            throw new CacheException(var5);
//        }
        return null;
    }

    @Override
    public Collection<V> values() {
        try {
            Set<K> keys = getRedisUtilInstance().hkeys(this.keyPrefix + "*");
            if (!CollectionUtils.isEmpty(keys)) {
                List<V> values = new ArrayList(keys.size());
                Iterator ikeys = keys.iterator();

                while(ikeys.hasNext()) {
                    K key = (K) ikeys.next();
                    V value = this.get(key);
                    if (value != null) {
                        values.add(value);
                    }
                }
                return Collections.unmodifiableList(values);
            } else {
                return Collections.emptyList();
            }
        } catch (Throwable var6) {
            throw new CacheException(var6);
        }
    }
}
