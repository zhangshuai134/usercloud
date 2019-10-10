package com.zs3.demo;

import redis.clients.jedis.Jedis;

/**
 * @Auther: curry.zhang
 * @Date: 2019/10/8 22:19
 * @Description:
 */
public class TestLuaRedis {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1");

        //redis 设置key=asd，value=12345
        String luaScript01 = "return redis.call('set','asd','12345')";
        jedis.evalsha(jedis.scriptLoad(luaScript01));

        // redis取值
        String luaScript = "return redis.call('get','asd')";
        String sha = jedis.scriptLoad(luaScript);
        System.out.println(sha);
        System.out.println(jedis.evalsha(sha));

        // 直接用jedis取值验证
        System.out.println(jedis.get("asd"));
    }
}
