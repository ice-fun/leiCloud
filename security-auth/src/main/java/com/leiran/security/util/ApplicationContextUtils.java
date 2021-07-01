package com.leiran.security.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.validation.constraints.NotNull;
import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * @author leiran
 * @Date 2020/12/9
 **/


public class ApplicationContextUtils implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static <T> T getSpringBean(String name) {
        return (T) context.getBean(name);
    }

    public static <T> T getSpringBean(Class<T> clazz) {
        return context.getBean(clazz);
    }

    public static <T> T getSpringBean(String name, Class<T> clazz) {
        return context.getBean(name, clazz);
    }

    public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationClass) {
        return context.getBeansWithAnnotation(annotationClass);
    }
}
