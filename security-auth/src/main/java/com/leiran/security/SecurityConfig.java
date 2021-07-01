package com.leiran.security;

import com.leiran.security.account.AdminService;
import com.leiran.security.account.UserService;
import com.leiran.security.configurer.JwtAuthorizeConfigurer;
import com.leiran.security.configurer.LoginConfigurer;
import com.leiran.security.filter.*;
import com.leiran.security.handler.fail.CodeLoginFailureHandler;
import com.leiran.security.handler.fail.LoginFailureHandler;
import com.leiran.security.handler.fail.RestAuthenticationAccessDeniedHandler;
import com.leiran.security.handler.fail.TokenAuthenticationFailureHandler;
import com.leiran.security.handler.success.*;
import com.leiran.security.log.LoginLogService;
import com.leiran.security.provider.*;
import com.leiran.security.token.*;
import com.leiran.security.util.RedisUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.header.Header;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.filter.CorsFilter;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Resource
    private RedisUtils redisUtils;

    @Resource
    private AdminService adminService;

    @Resource
    private UserService userService;

    @Resource
    private RestAuthenticationAccessDeniedHandler restAuthenticationAccessDeniedHandler;

    @Resource
    private TokenAuthenticationFailureHandler tokenAuthenticationFailureHandler;

    @Resource
    private LoginFailureHandler loginFailureHandler;

    @Resource
    private CodeLoginFailureHandler codeLoginFailureHandler;

    @Resource
    private AdminPasswordLoginSuccessHandler adminPasswordLoginSuccessHandler;

    @Resource
    private AdminCodeLoginSuccessHandler adminCodeLoginSuccessHandler;

    @Resource
    private AdminMiniProgramLoginSuccessHandler adminMiniProgramLoginSuccessHandler;

    @Resource
    private AdminWeChatLoginSuccessHandler adminWeChatLoginSuccessHandler;

    @Resource
    private UserCodeLoginSuccessHandler userCodeLoginSuccessHandler;

    @Resource
    private UserMiniProgramLoginSuccessHandler userMiniProgramLoginSuccessHandler;

    @Resource
    private UserPasswordLoginSuccessHandler userPasswordLoginSuccessHandler;

    @Resource
    private UserWeChatLoginSuccessHandler userWeChatLoginSuccessHandler;

    @Resource
    private LoginLogService loginLogService;

    /**
     * Provider
     * 配置各种登录方法的provider
     */

    protected JwtAuthenticationProvider adminJwtAuthenticationProvider() {
        return new JwtAuthenticationProvider(adminService, AdminJwtAuthenticationToken.class, "tokenVersion");
    }

    protected JwtAuthenticationProvider userJwtAuthenticationProvider() {
        return new JwtAuthenticationProvider(userService, UserJwtAuthenticationToken.class, "tokenVersion");
    }

    public AuthenticationProvider adminAuthentication() {
        DaoAuthenticationProvider provider = new AdminAuthenticationProvider();
        provider.setUserDetailsService(adminService);
        provider.setHideUserNotFoundExceptions(false);
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        return provider;
    }

    public AuthenticationProvider userAuthentication() {
        DaoAuthenticationProvider provider = new UserAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setHideUserNotFoundExceptions(false);
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        return provider;
    }


    protected VerifyCodeAuthenticationProvider adminCodeAuthenticationProvider() {
        return new VerifyCodeAuthenticationProvider(adminService, AdminVerifyCodeAuthenticationToken.class, redisUtils);
    }

    protected MiniProgramAuthenticationProvider adminMiniProgramAuthenticationProvider() {
        return new MiniProgramAuthenticationProvider(adminService, AdminMiniProgramAuthenticationToken.class, loginLogService);
    }

    protected WeChatAuthenticationProvider adminWeChatAuthenticationProvider() {
        return new WeChatAuthenticationProvider(adminService, AdminWeChatAuthenticationToken.class);
    }


    protected VerifyCodeAuthenticationProvider userCodeAuthenticationProvider() {
        return new VerifyCodeAuthenticationProvider(userService, UserVerifyCodeAuthenticationToken.class, redisUtils);
    }

    protected MiniProgramAuthenticationProvider userMiniProgramAuthenticationProvider() {
        return new MiniProgramAuthenticationProvider(userService, UserMiniProgramAuthenticationToken.class, loginLogService);
    }

    protected WeChatAuthenticationProvider userWeChatAuthenticationProvider() {
        return new WeChatAuthenticationProvider(userService, UserWeChatAuthenticationToken.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth
                .authenticationProvider(adminAuthentication())
                .authenticationProvider(adminJwtAuthenticationProvider())
                .authenticationProvider(adminWeChatAuthenticationProvider())
                .authenticationProvider(adminCodeAuthenticationProvider())
                .authenticationProvider(adminMiniProgramAuthenticationProvider())
                .authenticationProvider(userJwtAuthenticationProvider())
                .authenticationProvider(userCodeAuthenticationProvider())
                .authenticationProvider(userMiniProgramAuthenticationProvider())
                .authenticationProvider(userAuthentication())
                .authenticationProvider(userWeChatAuthenticationProvider())
        ;


    }

    protected WebSocketRequestFilter configWebSocketRequestFilter(String path, Class<? extends JwtAuthenticationToken> authClass,
                                                                  AuthenticationSuccessHandler authenticationSuccessHandler,
                                                                  AuthenticationFailureHandler authenticationFailureHandler, String... excludeUrl) {
        WebSocketRequestFilter filter = new WebSocketRequestFilter(path, authClass);
        filter.setPermissiveUrl(excludeUrl);
        filter.setSuccessHandler(authenticationSuccessHandler);
        filter.setFailureHandler(authenticationFailureHandler);
        return filter;
    }

    protected List<AbstractAuthenticationProcessingFilter> loginFilters() {
        List<AbstractAuthenticationProcessingFilter> filters = new ArrayList<>();
        filters.add(new VerifyCodeAuthenticationFilter("/user/login/phone", UserVerifyCodeAuthenticationToken.class, userCodeLoginSuccessHandler, codeLoginFailureHandler));
        filters.add(new VerifyCodeAuthenticationFilter("/admin/login/phone", AdminVerifyCodeAuthenticationToken.class, adminCodeLoginSuccessHandler, codeLoginFailureHandler));
        filters.add(new PasswordAuthenticationFilter("/admin/login/account", AdminUsernamePasswordAuthenticationToken.class, adminPasswordLoginSuccessHandler, loginFailureHandler));
        filters.add(new PasswordAuthenticationFilter("/user/login/account", UserUsernamePasswordAuthenticationToken.class, userPasswordLoginSuccessHandler, loginFailureHandler));
        filters.add(new MiniProgramAuthenticationFilter("/user/login/miniProgram", UserMiniProgramAuthenticationToken.class, userMiniProgramLoginSuccessHandler, loginFailureHandler));
        filters.add(new MiniProgramAuthenticationFilter("/admin/login/miniProgram", AdminMiniProgramAuthenticationToken.class, adminMiniProgramLoginSuccessHandler, loginFailureHandler));
        filters.add(new WeChatAuthenticationFilter("/admin/login/official", AdminWeChatAuthenticationToken.class, adminWeChatLoginSuccessHandler, codeLoginFailureHandler));
        filters.add(new WeChatAuthenticationFilter("/user/login/official", UserWeChatAuthenticationToken.class, userWeChatLoginSuccessHandler, codeLoginFailureHandler));
        return filters;
    }

    @Override
    public void configure(WebSecurity web) {

    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling()
                .authenticationEntryPoint(new MyAuthenticationEntryPoint())
                .accessDeniedHandler(restAuthenticationAccessDeniedHandler)
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .formLogin().disable()
                .sessionManagement().disable()
                .cors()
                .and()
                .headers().addHeaderWriter(new StaticHeadersWriter(Arrays.asList(new Header("Access-control-Allow-Origin", "*"), new Header("Access-Control-Expose-Headers", "generateAuthorization"))))
                .and()
                .addFilterAfter(new OptionsRequestFilter(), CorsFilter.class)
                //配置token认证
                .apply(new JwtAuthorizeConfigurer<>())
                .addJwtAuthenticationFilter("/admin/**", AdminJwtAuthenticationToken.class, null, tokenAuthenticationFailureHandler,
                        "/admin/login/account", "/admin/login/miniProgram", "/admin/login/phone", "/admin/login/official")
                .addJwtAuthenticationFilter("/api/**", UserJwtAuthenticationToken.class, null, tokenAuthenticationFailureHandler)
                .addJwtAuthenticationFilter("/user/**", UserJwtAuthenticationToken.class, null, tokenAuthenticationFailureHandler,
                        "/user/login/account", "/user/login/miniProgram", "/user/login/phone", "/user/login/official")
                //配置登陆
                .and()
                .apply(new LoginConfigurer<>(loginFilters()))
                .and()
        ;
    }
}
