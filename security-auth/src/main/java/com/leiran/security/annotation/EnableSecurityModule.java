package com.leiran.security.annotation;

import com.leiran.security.InjectConfig;
import com.leiran.security.SecurityConfig;
import com.leiran.security.util.RedisConfig;
import com.leiran.security.util.RedisUtils;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.lang.annotation.*;

/**
 * @author Lei Ran
 **/

@Documented
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import({InjectConfig.class, SecurityConfig.class, RedisConfig.class, RedisUtils.class})
public @interface EnableSecurityModule {
}
