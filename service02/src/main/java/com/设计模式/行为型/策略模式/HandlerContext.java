package com.设计模式.行为型.策略模式;

import com.设计模式.行为型.策略模式.handler.AbstractHandler;
import com.设计模式.行为型.策略模式.util.SpringContextUtils;

import java.util.Map;

/**
 * @Auther: curry.zhang
 * @Date: 2019/9/20 19:09
 * @Description:
 */
public class HandlerContext {
    private Map<String,Class> handlerMap;

    public HandlerContext(Map<String, Class> handlerMap) {
        this.handlerMap = handlerMap;
    }

    public AbstractHandler getInstance(String type){
        Class clazz = handlerMap.get(type);
        if(clazz == null){
            throw new IllegalArgumentException("没有type对应的处理器，type："+type);
        }
        return (AbstractHandler)SpringContextUtils.getBean(clazz);
    }
}
