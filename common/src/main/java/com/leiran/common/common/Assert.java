package com.leiran.common.common;


import com.leiran.common.exception.CustomFailureException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

public abstract class Assert {
    public Assert() {
    }

    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new CustomFailureException(message);
        }
    }

    public static void isFalse(boolean expression, String message) {
        isTrue(!expression, message);
    }


    public static void isNull(Object object, String message) {
        if (object != null) {
            throw new CustomFailureException(message);
        }
    }


    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new CustomFailureException(message);
        }
    }

    public static void notBlank(String text, String message) {
        if (!StringUtils.hasText(text)) {
            throw new CustomFailureException(message);
        }
    }

    public static void isEqual(Object object, Object object2, String message) {
        if (!object.equals(object2)) {
            throw new CustomFailureException(message);
        }
    }

    public static void notEqual(Object object, Object object2, String message) {
        if (object.equals(object2)) {
            throw new CustomFailureException(message);
        }
    }


    public static void contains(Collection<?> collection, Object element, String message) {
        if (!collection.contains(element)) {
            throw new CustomFailureException(message);
        }
    }

    public static void doesNotContain(String textToSearch, String substring, String message) {
        if (StringUtils.hasLength(textToSearch) && StringUtils.hasLength(substring) && textToSearch.contains(substring)) {
            throw new CustomFailureException(message);
        }
    }

    public static void notEmpty(Object[] array, String message) {
        if (ObjectUtils.isEmpty(array)) {
            throw new CustomFailureException(message);
        }
    }

    public static void noNullElements(Object[] array, String message) {
        if (array != null) {
            for (Object element : array) {
                if (element == null) {
                    throw new CustomFailureException(message);
                }
            }
        }

    }


    public static void notEmpty(Collection<?> collection, String message) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new CustomFailureException(message);
        }
    }


    public static void noNullElements(Collection<?> collection, String message) {
        if (collection != null) {
            for (Object element : collection) {
                if (element == null) {
                    throw new CustomFailureException(message);
                }
            }
        }

    }

    public static void notEmpty(Map<?, ?> map, String message) {
        if (CollectionUtils.isEmpty(map)) {
            throw new CustomFailureException(message);
        }
    }

    public static void isInstanceOf(Class<?> type, Object obj, String message) {
        notNull(type, (String) "Type to check against must not be null");
        if (!type.isInstance(obj)) {
            instanceCheckFailed(type, obj, message);
        }

    }

    public static void isInstanceOf(Class<?> type, Object obj) {
        isInstanceOf(type, obj, "");
    }

    public static void isAssignable(Class<?> superType, Class<?> subType, String message) {
        notNull(superType, (String) "Super type to check against must not be null");
        if (subType == null || !superType.isAssignableFrom(subType)) {
            assignableCheckFailed(superType, subType, message);
        }

    }

    public static void isAssignable(Class<?> superType, Class<?> subType, Supplier<String> messageSupplier) {
        notNull(superType, (String) "Super type to check against must not be null");
        if (subType == null || !superType.isAssignableFrom(subType)) {
            assignableCheckFailed(superType, subType, nullSafeGet(messageSupplier));
        }

    }

    public static void isAssignable(Class<?> superType, Class<?> subType) {
        isAssignable(superType, subType, "");
    }

    private static void instanceCheckFailed(Class<?> type, Object obj, String msg) {
        String className = obj != null ? obj.getClass().getName() : "null";
        String result = "";
        boolean defaultMessage = true;
        if (StringUtils.hasLength(msg)) {
            if (endsWithSeparator(msg)) {
                result = msg + " ";
            } else {
                result = messageWithTypeName(msg, className);
                defaultMessage = false;
            }
        }

        if (defaultMessage) {
            result = result + "Object of class [" + className + "] must be an instance of " + type;
        }

        throw new CustomFailureException(result);
    }

    private static void assignableCheckFailed(Class<?> superType, Class<?> subType, String msg) {
        String result = "";
        boolean defaultMessage = true;
        if (StringUtils.hasLength(msg)) {
            if (endsWithSeparator(msg)) {
                result = msg + " ";
            } else {
                result = messageWithTypeName(msg, subType);
                defaultMessage = false;
            }
        }

        if (defaultMessage) {
            result = result + subType + " is not assignable to " + superType;
        }

        throw new CustomFailureException(result);
    }

    private static boolean endsWithSeparator(String msg) {
        return msg.endsWith(":") || msg.endsWith(";") || msg.endsWith(",") || msg.endsWith(".");
    }

    private static String messageWithTypeName(String msg, Object typeName) {
        return msg + (msg.endsWith(" ") ? "" : ": ") + typeName;
    }


    private static String nullSafeGet(Supplier<String> messageSupplier) {
        return messageSupplier != null ? (String) messageSupplier.get() : null;
    }
}

