package com.leiran.log.aop;

import com.leiran.log.config.LogConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;


@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Configuration
@Import({LogConfig.class})
public @interface EnableAopLog {
}
