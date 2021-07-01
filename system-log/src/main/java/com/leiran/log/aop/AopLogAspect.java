package com.leiran.log.aop;


import com.leiran.common.bean.system.po.SystemLog;
import com.leiran.common.common.Result;
import com.leiran.common.exception.CustomFailureException;
import com.leiran.common.utils.IpUtil;
import com.leiran.common.utils.JSONUtils;
import com.leiran.log.service.SystemLogService;
import com.leiran.security.AuthUserDetails;
import com.leiran.security.exception.AuthException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.*;

/**
 * @author leiran
 *
 * <p>
 * 基于 AOP 的日志收集模块
 * 根据注解 {@code AopLog} 织入，实现对接口的日志收集。包含接口路径，用户信息，ip地址，请求参数，响应结果，异常信息
 * 通过spring@Bean的方式装配到系统中
 */
@Slf4j
@Aspect
public class AopLogAspect {

    private SystemLogService systemLogService;

    private Class<?> responseClass = Result.class;
    private MultiValueMap<String, Object> successCondition = null;
    private final List<Class<? extends RuntimeException>> ignoreExceptions = new ArrayList<>(
            Arrays.asList(CustomFailureException.class, AuthException.class)
    );


    /**
     * 可自定义需要保存哪些headers参数
     */
    private final String[] headerNames = {"token", "content-type"};

    public void setSystemLogService(SystemLogService systemLogService) {
        this.systemLogService = systemLogService;
    }

    /**
     * 设置接口统一返回类，以及业务成功的条件，用于自动判断是否业务成功，不设置的情况，全部归为业务成功
     */
    public void setResponseClass(Class<?> responseClass, String key, Object... values) {
        if (responseClass != null) {
            this.responseClass = responseClass;
        }
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        for (Object o : values) {
            map.add(key, o);
        }
        this.successCondition = map;
    }

    /**
     * 根据注解切入
     */
    @Pointcut("@annotation(com.leiran.log.aop.AopLog)")
    public void controllerAspect() {
    }

    @Around("controllerAspect()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) {
        AopLog aopLog = ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod().getAnnotation(AopLog.class);
        boolean saveLog = aopLog.saveLog();
        try {
            Object result = proceedingJoinPoint.proceed();
            // 保存日志
            if (saveLog) {
                saveNormalLog(proceedingJoinPoint, result);
            }
            return result;
        } catch (Throwable throwable) {
            saveExceptionLog(proceedingJoinPoint, throwable);
            throw new RuntimeException(throwable);
        }
    }

    private SystemLog createLogBase(ProceedingJoinPoint proceedingJoinPoint) {
        SystemLog systemLog = new SystemLog();
        //获取请求相关参数
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            String path = request.getRequestURI();
            String method = request.getMethod();
            Map<String, String> header = new HashMap<>();
            for (String name : headerNames) {
                header.put(name, request.getHeader(name));
            }
            String ip = IpUtil.getIpAddress(request);
            systemLog.setPath(path);
            systemLog.setMethod(method);
            systemLog.setIp(ip);
            systemLog.setHeader(JSONUtils.toJSONString(header));
        }

        //获取用户相关参数
        SecurityContext context = SecurityContextHolder.getContext();
        String userId = null;
        String username = null;
        if (context.getAuthentication() != null) {
            AuthUserDetails userDetails = (AuthUserDetails) context.getAuthentication().getPrincipal();
            userId = userDetails.getId();
            username = userDetails.getName();
        }

        //获取注解上参数
        AopLog aopLog = ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod().getAnnotation(AopLog.class);
        String logName = aopLog.name();
        /*
         * 如果 platform 不足以区分属于哪个终端，
         * 比如Android app 、 ios app 、小程序、H5 使用同一个接口的情况下
         * 可以通过 headers 添加参数来区分。
         */
        String logPlatform = aopLog.platform();

        //获取方法中的参数
        Object[] args = proceedingJoinPoint.getArgs();
        HashMap<String, Object> params = new HashMap<>();

        for (Object arg : args) {
            // HttpServletRequest HttpServletResponse 不能序列化，否则会报错
            // UserDetails Authentication 属于用户信息，较为敏感，不记录在日志
            if ((arg instanceof Authentication)
                    || (arg instanceof UserDetails)
                    || (arg instanceof HttpServletRequest)
                    || (arg instanceof HttpServletResponse)) {
                continue;
            }
            try {
                params.put(arg.getClass().getSimpleName(), arg);
            } catch (Exception ignored) {
            }

        }
        if (params.size() == 1) {
            params.keySet().stream().findFirst().ifPresent(key -> systemLog.setParam(JSONUtils.toJSONString(params.get(key))));
        } else {
            systemLog.setParam(JSONUtils.toJSONString(params));
        }
        systemLog.setName(logName);
        systemLog.setPlatform(logPlatform);
        systemLog.setUserId(userId);
        systemLog.setUsername(username);

        return systemLog;
    }

    private void saveExceptionLog(ProceedingJoinPoint proceedingJoinPoint, Throwable throwable) {
        Class<? extends Throwable> aClass = throwable.getClass();
        if (ignoreExceptions.contains(aClass)) {
            saveIgnoreExceptionLog(proceedingJoinPoint, throwable);
            return;
        }
        SystemLog base = createLogBase(proceedingJoinPoint);
        final Writer writer = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(writer);
        throwable.printStackTrace(printWriter);
        base.setException(writer.toString());
        base.setType("异常");
        finalSave(base);
    }

    public void saveNormalLog(ProceedingJoinPoint proceedingJoinPoint, Object result) {
        SystemLog base = createLogBase(proceedingJoinPoint);
        String string = JSONUtils.toJSONString(result);
        base.setResult(string);
        base.setType("");
        // 如果设置了 响应类以及成功条件
        if (result != null && result.getClass().equals(responseClass) && successCondition != null) {
            Set<String> strings = successCondition.keySet();
            String key = strings.stream().findFirst().orElse(null);
            if (key == null) {
                finalSave(base);
                return;
            }
            List<Object> list = successCondition.get(key);
            LinkedHashMap<?, ?> object = JSONUtils.parseObject(string);
            Object o = object.get(key);
            boolean flag = list.stream().anyMatch(o1 -> o1.equals(o));
            base.setType(flag ? "成功" : "失败");
        }
        finalSave(base);
    }

    /**
     * 忽略的异常 归为失败的情况，而非异常情况
     * 适用于自定义的Assert方法
     *
     * @param proceedingJoinPoint proceedingJoinPoint
     * @param throwable           throwable
     */
    private void saveIgnoreExceptionLog(ProceedingJoinPoint proceedingJoinPoint, Throwable throwable) {
        SystemLog base = createLogBase(proceedingJoinPoint);
        String message = throwable.getLocalizedMessage();
        base.setResult(JSONUtils.toJSONString(message));
        base.setType("错误");
        finalSave(base);
    }

    /**
     * 保存
     *
     * @param log 日志数据
     */
    private void finalSave(SystemLog log) {
        new Thread(() -> systemLogService.saveLog(log)).start();
    }
}
