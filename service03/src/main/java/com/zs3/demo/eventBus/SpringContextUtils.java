package com.zs3.demo.eventBus;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Auther: curry.zhang
 * @Date: 2019/9/11 10:21
 * @Description: 从 Spring 容器中获取 Bean，用于一些不方便采用注入的地方
 */
@Component
public class SpringContextUtils implements BeanFactoryPostProcessor {

    private static ConfigurableListableBeanFactory beanFactory;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        SpringContextUtils.beanFactory = configurableListableBeanFactory;
    }

    public static <T> T getBean(String name) throws BeansException {
        return (T) beanFactory.getBean(name);
    }

    public static <T> T getBean(Class<T> clz) throws BeansException {
        T result = beanFactory.getBean(clz);
        return result;
    }

    public static <T> List<T> getBeansOfType(Class<T> type) {
        return beanFactory.getBeansOfType(type).entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toList());
    }

    // 上面的例子用到了这个
    public static List<Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) {
        Map<String, Object> beansWithAnnotation = beanFactory.getBeansWithAnnotation(annotationType);

        // java 8 的写法，将 map 的 value 收集起来到一个 list 中
        return beansWithAnnotation.entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toList());

        // java 7
//        List<Object> result = new ArrayList<>();
//        for (Map.Entry<String, Object> entry : beansWithAnnotation.entrySet()) {
//            result.add(entry.getValue());
//        }
//        return result;
    }
}
