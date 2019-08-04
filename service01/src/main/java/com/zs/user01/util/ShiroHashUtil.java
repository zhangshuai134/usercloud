package com.zs.user01.util;

import com.zs.user01.contant.WebConstant;
import org.apache.shiro.crypto.hash.SimpleHash;


public class ShiroHashUtil {

    public static String hash(String param){
        SimpleHash hash=new SimpleHash(WebConstant.SHIRO_HASH_ALGORITHM_NAME,param,null,WebConstant.SHIRO_HASH_ITERATIONS);
        return hash.toString().toUpperCase();
    }

    public static void main(String[] args){
        String password= RandomPassworUtil.generated(6);
        System.out.println("password:"+password);
        System.out.println("hashpassword:"+ShiroHashUtil.hash(password));
        System.out.println("userid:"+ UuidGenerator.generateUuidStr());

    }
}
