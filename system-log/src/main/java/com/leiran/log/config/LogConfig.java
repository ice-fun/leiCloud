package com.leiran.log.config;

import com.leiran.common.common.Result;
import com.leiran.log.aop.AopLogAspect;
import com.leiran.log.service.SystemLogService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogConfig {

    @Bean
    public SystemLogService systemLogService() {
        return new SystemLogService();
    }

    @Bean
    public AopLogAspect aopLogAspect() {
        AopLogAspect aopLogAspect = new AopLogAspect();
        aopLogAspect.setSystemLogService(systemLogService());
        aopLogAspect.setResponseClass(Result.class, "statusCode", 200);
        return aopLogAspect;
    }


}
