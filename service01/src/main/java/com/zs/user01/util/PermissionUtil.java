package com.zs.user01.util;


import com.google.common.collect.Maps;

import java.util.Map;


public class PermissionUtil {

    public static Map<String,String> getPermission(Map<String,String> map){
        Map<String,String> filterChainDefinitionMap = Maps.newLinkedHashMap();
        filterChainDefinitionMap.put("/api/web/logout", "anon");
        filterChainDefinitionMap.put("/api/web/unauth", "anon");
        filterChainDefinitionMap.put("/api/web/kickout", "anon");
        filterChainDefinitionMap.put("/api/web/check", "anon");
        filterChainDefinitionMap.put("/api/web/defaultKaptcha", "anon");
        filterChainDefinitionMap.put("/api/account/forgetPassword", "anon");
        filterChainDefinitionMap.put("/api/web/login", "captcha");
        filterChainDefinitionMap.put("/api/account/getAccountInfo", "authc");
        filterChainDefinitionMap.put("/api/user/getUserByRoleName", "authc");
        filterChainDefinitionMap.putAll(map);
        filterChainDefinitionMap.put("/api/**", "authc");
        return filterChainDefinitionMap;
    }

}
