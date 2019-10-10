package com.zs3.demo.lua;

import com.zs3.demo.RedisUtil;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: curry.zhang
 * @Date: 2019/10/8 19:54
 * @Description:
 */
@Service
public class TestLuaJ {

    @Autowired
    private RedisTemplate redisTemplate;

    private RedisUtil getRedisUtilInstance() {
        return RedisUtil.getInstance(redisTemplate);
    }

    private DefaultRedisScript<List> getRedisScript;

    public String get() {
        System.out.println("testLuaJ get方法");
        String asd = (String) getRedisUtilInstance().get("asd");
        System.out.println(asd);
        return asd;
    }

    public void set() {
        System.out.println("testLuaJ set方法");
        getRedisUtilInstance().set("asd", "asdasdads");
    }

    @PostConstruct
    public void init() {
        getRedisScript = new DefaultRedisScript<List>();
        getRedisScript.setResultType(List.class);
        String luaPath = "D:\\Users\\curry.zhang\\IdeaProjects\\usercloud\\service03\\lua\\test.lua";
        getRedisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource(luaPath)));
    }

    public void redisAddScriptExec() {
        DefaultRedisScript<Boolean> script=new DefaultRedisScript<>();

        ClassPathResource resource=new ClassPathResource("lua/login.lua");

        script.setScriptSource(new ResourceScriptSource(resource));

        script.setResultType(Boolean.class);

        List<String> keys=new ArrayList();

        keys.add("k10");

        keys.add("k11");


        Boolean result=(Boolean)redisTemplate.execute(script,keys,new String[]{"lala","lala"});

        System.out.println(result);

    }

    public static void main(String[] args) throws FileNotFoundException {
        String luaStr = "print 'hello world'";
        Globals globals = JsePlatform.standardGlobals();
        LuaValue chunk = globals.load(luaStr);
        chunk.call();
//
//        String luaPath = "D:\\Users\\curry.zhang\\IdeaProjects\\usercloud\\service03\\lua\\login.lua";   //lua脚本文件所在路径
//        Globals globals = JsePlatform.standardGlobals();
//        globals.load(new InputStreamReader(new FileInputStream(new File(luaPath))), "chunkname").call();
//        LuaValue func = globals.get(LuaValue.valueOf("test1"));
//        func.call();
//
//        LuaValue func1 = globals.get(LuaValue.valueOf("test2"));
//        String data = func1.call(LuaValue.valueOf("java入参")).toString();
//        System.out.println("lua文件返回值:" + data);
    }
}
