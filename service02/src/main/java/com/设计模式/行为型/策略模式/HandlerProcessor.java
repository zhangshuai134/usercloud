package com.设计模式.行为型.策略模式;

import com.设计模式.行为型.策略模式.util.ClassScaner;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: curry.zhang
 * @Date: 2019/9/20 19:17
 * @Description:
 */
@Component
@SuppressWarnings("unchecked")
public class HandlerProcessor implements BeanFactoryPostProcessor {

    private static final String HANDLER_PACKAGE = "com.设计模式.行为型.策略模式.handler";

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Map<String, Class> handlerMap = new HashMap<>();
        ClassScaner.scan(HANDLER_PACKAGE,HandlerType.class).forEach(clazz ->{
            Annotation annotation = clazz.getAnnotation(HandlerType.class);
            HandlerType handlerType = (HandlerType) annotation;
            String type = handlerType.value();
            System.out.println(type);
            handlerMap.put(type,clazz);
        });
        HandlerContext handlerContext = new HandlerContext(handlerMap);
        beanFactory.registerSingleton(HandlerContext.class.getName(),handlerContext);
    }
}
