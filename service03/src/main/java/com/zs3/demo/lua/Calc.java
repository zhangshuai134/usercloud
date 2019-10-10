package com.zs3.demo.lua;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

/**
 * @Auther: curry.zhang
 * @Date: 2019/10/8 20:00
 * @Description:
 */
public class Calc {
    public static void main(String[] args) {
        long key = getKey();
        System.out.println(key);
        System.out.println(getKeyJava());

    }

    public static long getKey() {
        //获取一个lua的运行环境，lua虚拟机应该就在这里了
        //lua是个弱类型语言，在java里，所有从lua获得的，或者要传递给lua的，都是LuaValue对象
        LuaValue _G = JsePlatform.standardGlobals();
        //执行gen.lua脚本
        //_G.get("dofile")获取dofile方法的对象
        //get其实是获取table值的方法，dofile就是全局table的一个值
        //对于Function类型的对象可以用call方法去调用，参数就是lua方法需要的参数，但是一定要转换成LuaValue类型
        _G.get("dofile").call(LuaValue.valueOf("/lua/gen.lua"));
        //上一句执行完以后，gen.lua中的genkey函数就在全局变量中了，
        //可以这样直接调用
        LuaValue key = _G.get("genkey").call();
        return key.checklong();
    }
    //用java实现的，比较用
    //value = (int)(timestamp / 1000 ) ^ (int)(timestamp / 400)
    public static long getKeyJava() {
        long tm = System.currentTimeMillis() / 1000;
        return (tm / 1000) ^ (tm / 400);
    }
}
