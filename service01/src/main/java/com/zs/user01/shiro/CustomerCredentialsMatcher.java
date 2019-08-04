package com.zs.user01.shiro;

import com.zs.user01.contant.WebConstant;
import com.zs.user01.util.RedisUtil;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;



public class CustomerCredentialsMatcher extends HashedCredentialsMatcher {

    @Autowired
    private RedisTemplate redisTemplate;

    private RedisUtil getRedisUtilInstance(){
        return RedisUtil.getInstance(redisTemplate);
    }



    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String loginName = (String) token.getPrincipal();//获取登录用户名
        String key= WebConstant.LOGIN_ERROR_TIMES_PREFIX_KEY+loginName;
        Integer value = (Integer) getRedisUtilInstance().get(key);
        int errorNum=0;
            if (value!=null) {
                errorNum = value;
            }
            if (errorNum >= WebConstant.LOGIN_ERROR_TIMES) {
                throw new LockedAccountException(); //抛出账号锁定异常类
            }
        boolean matches = super.doCredentialsMatch(token, info);    //判断用户是否可用，即是否为正确的账号密码
            if (matches) {
                getRedisUtilInstance().delete(key);
               return matches;
            } else {
                //存储错误次数到redis中
                getRedisUtilInstance().set(key,errorNum+1,WebConstant.LOGIN_ERROR_LIMIT_SECOND, TimeUnit.SECONDS.name());
            }
        return matches;
    }


}
