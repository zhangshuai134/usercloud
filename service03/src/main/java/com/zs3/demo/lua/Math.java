package com.zs3.demo.lua;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.ZeroArgFunction;

/**
 * @Auther: curry.zhang
 * @Date: 2019/10/8 20:01
 * @Description:
 */
public class Math extends OneArgFunction {
    //lua的方法都是闭包，在java中一定是用类对象与之对应的。
    //于是call这个方法就是调用闭包使用所调用的方法，必须实现
    public LuaValue call(LuaValue modname) {
        //这是类在lua里是一个模块，也就是个函数包，在lua里也就是一个table
        //table的每一个元素是一个函数(闭包而已)
        //这个lib就是一个table，用来存放各个lua模块方法
        LuaValue lib = tableOf();
        //设置timestamp方法
        lib.set("timestamp", new lua_timestamp());
        //设置异或方法
        lib.set("bitxor", new lua_bitxor());
        //这里不确定：env是该类的环境参数，暂时没研究这一句的作用,本例中不设置也没关系
        //env.set(modname.checkjstring(), lib);
        return lib;
    }

    static class lua_timestamp extends ZeroArgFunction {
        public LuaValue call() {
            return LuaValue.valueOf(System.currentTimeMillis() / 1000);
        }
    }
    static class lua_bitxor extends TwoArgFunction {
        public LuaValue call(LuaValue a, LuaValue b) {
            //lua传进来参数都是LuaValue的，java使用的时候需要使用相应的check方法转为本地变量
            long pa = a.checklong();
            long pb = b.checklong();
            long r = pa ^ pb;
            //返回的时候需要用valueOf方法转为LuaValue类型
            return LuaValue.valueOf(r);
        }
    }
}
