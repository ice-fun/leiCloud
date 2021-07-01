package com.leiran.common.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.esotericsoftware.reflectasm.ConstructorAccess;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.util.CollectionUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.String.format;

public class BeanKit {
    private static final Map<String, BeanCopier> BEAN_COPIER_CACHE = new ConcurrentHashMap<>();
    private static final Map<String, ConstructorAccess> CONSTRUCTOR_ACCESS_CACHE = new ConcurrentHashMap<>();

    private static void copy(Object source, Object target) {
        BeanCopier copier = getBeanCopier(source.getClass(), target.getClass());
        copier.copy(source, target, null);
    }

    private static BeanCopier getBeanCopier(Class<?> sourceClass, Class<?> targetClass) {
        String beanKey = generateKey(sourceClass, targetClass);
        BeanCopier copier;
        if (!BEAN_COPIER_CACHE.containsKey(beanKey)) {
            copier = BeanCopier.create(sourceClass, targetClass, false);
            BEAN_COPIER_CACHE.put(beanKey, copier);
        } else {
            copier = BEAN_COPIER_CACHE.get(beanKey);
        }
        return copier;
    }

    private static String generateKey(Class<?> class1, Class<?> class2) {
        return class1.toString() + class2.toString();
    }

    /**
     * copy bean
     *
     * @param source      源
     * @param targetClass 目标类class
     * @param <T>         T
     * @return 新的对象
     */
    public static <T> T copy(Object source, Class<T> targetClass) {
        T t;
        try {
            t = targetClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(format("Create new instance of %s failed: %s", targetClass, e.getMessage()));
        }
        if (source != null) {
            copy(source, t);
        }
        return t;
    }

    public static <T> List<T> copyPropertiesOfList(List<?> sourceList, Class<T> targetClass) {
        if (CollectionUtils.isEmpty(sourceList)) {
            return Collections.emptyList();
        }
        ConstructorAccess<T> constructorAccess = getConstructorAccess(targetClass);
        List<T> resultList = new ArrayList<>(sourceList.size());
        for (Object o : sourceList) {
            try {
                T t = constructorAccess.newInstance();
                copy(o, t);
                resultList.add(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return resultList;
    }

    private static <T> ConstructorAccess<T> getConstructorAccess(Class<T> targetClass) {
        ConstructorAccess<T> constructorAccess = CONSTRUCTOR_ACCESS_CACHE.get(targetClass.toString());
        if (constructorAccess != null) {
            return constructorAccess;
        }
        try {
            constructorAccess = ConstructorAccess.get(targetClass);
            constructorAccess.newInstance();
            CONSTRUCTOR_ACCESS_CACHE.put(targetClass.toString(), constructorAccess);
        } catch (Exception e) {
            throw new RuntimeException(format("Create new instance of %s failed: %s", targetClass, e.getMessage()));
        }
        return constructorAccess;
    }

    public static void copyNotNullValues(Object source, Object target) {
        BeanUtil.copyProperties(source, target, CopyOptions.create().setIgnoreNullValue(true).setIgnoreError(true));
    }

    public static Map<String, Object> toMap(Object obj) {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                if (!key.equals("class") && !key.equals("pageNo") && !key.equals("pageSize")) {
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            System.out.println("transBean2Map Error " + e);
        }
        return map;
    }

    public static <T> List<T> JSONArrayToList(List<?> sourceList, Class<T> targetClass) {
        if (CollectionUtils.isEmpty(sourceList)) {
            return Collections.emptyList();
        }
        List<T> resultList = new ArrayList<>(sourceList.size());
        for (Object o : sourceList) {
            T t;
            try {
                t = JSONUtils.parseObject(JSONUtils.toJSONString(o), targetClass);
                resultList.add(t);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return resultList;
    }
}
