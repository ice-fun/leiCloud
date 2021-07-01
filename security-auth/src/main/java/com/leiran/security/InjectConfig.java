package com.leiran.security;

import com.leiran.security.account.AdminService;
import com.leiran.security.account.UserService;
import com.leiran.security.handler.fail.CodeLoginFailureHandler;
import com.leiran.security.handler.fail.LoginFailureHandler;
import com.leiran.security.handler.fail.RestAuthenticationAccessDeniedHandler;
import com.leiran.security.handler.fail.TokenAuthenticationFailureHandler;
import com.leiran.security.handler.success.*;
import com.leiran.security.log.LoginLogService;
import com.leiran.security.util.AllowVisitorUtils;
import com.leiran.security.util.ApplicationContextUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
public class InjectConfig {
    //配置加密类
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public ApplicationContextUtils applicationContextUtils() {
        return new ApplicationContextUtils();
    }

    @Bean
    public AllowVisitorUtils allowVisitorUtils() {
        applicationContextUtils();
        return new AllowVisitorUtils();
    }

    @Bean
    public UserService userService() {
        return new UserService();
    }

    @Bean
    public AdminService adminService() {
        return new AdminService();
    }

    @Bean
    public RestAuthenticationAccessDeniedHandler restAuthenticationAccessDeniedHandler() {
        return new RestAuthenticationAccessDeniedHandler();
    }

    @Bean
    public TokenAuthenticationFailureHandler tokenAuthenticationFailureHandler() {
        return new TokenAuthenticationFailureHandler();
    }

    /**
     * Handler
     */

    @Bean
    public LoginFailureHandler loginFailureHandler() {
        return new LoginFailureHandler();
    }

    @Bean
    public CodeLoginFailureHandler codeLoginFailureHandler() {
        return new CodeLoginFailureHandler();
    }

    @Bean
    public AdminPasswordLoginSuccessHandler adminPasswordLoginSuccessHandler() {
        return new AdminPasswordLoginSuccessHandler();
    }

    @Bean
    public AdminCodeLoginSuccessHandler adminCodeLoginSuccessHandler() {
        return new AdminCodeLoginSuccessHandler();
    }

    @Bean
    public AdminMiniProgramLoginSuccessHandler adminMiniProgramLoginSuccessHandler() {
        return new AdminMiniProgramLoginSuccessHandler();
    }

    @Bean
    public AdminWeChatLoginSuccessHandler adminWeChatLoginSuccessHandler() {
        return new AdminWeChatLoginSuccessHandler();
    }

    @Bean
    public UserCodeLoginSuccessHandler userCodeLoginSuccessHandler() {
        return new UserCodeLoginSuccessHandler();
    }

    @Bean
    public UserMiniProgramLoginSuccessHandler userMiniProgramLoginSuccessHandler() {
        return new UserMiniProgramLoginSuccessHandler();
    }

    @Bean
    public UserPasswordLoginSuccessHandler userPasswordLoginSuccessHandler() {
        return new UserPasswordLoginSuccessHandler();
    }

    @Bean
    public UserWeChatLoginSuccessHandler userWeChatLoginSuccessHandler() {
        return new UserWeChatLoginSuccessHandler();
    }

    @Bean
    public LoginLogService sysLogService() {
        return new LoginLogService();
    }
}
