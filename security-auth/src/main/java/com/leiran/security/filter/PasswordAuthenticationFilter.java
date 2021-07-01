package com.leiran.security.filter;

import com.leiran.common.common.Assert;
import com.leiran.common.utils.JSONUtils;
import lombok.SneakyThrows;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;

/**
 * 账号密码登录拦截器
 */
public class PasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    protected String username = null, password = null, macode = null;
    private final Class<? extends UsernamePasswordAuthenticationToken> auth;

    public PasswordAuthenticationFilter(String loginPattern, Class<? extends UsernamePasswordAuthenticationToken> auth) {
        super(new AntPathRequestMatcher(loginPattern, "POST"));
        setSessionAuthenticationStrategy(new NullAuthenticatedSessionStrategy());
        this.auth = auth;
    }

    public PasswordAuthenticationFilter(String loginPattern, Class<? extends UsernamePasswordAuthenticationToken> auth,
                                        AuthenticationSuccessHandler successHandler, AuthenticationFailureHandler failureHandler) {
        super(new AntPathRequestMatcher(loginPattern, "POST"));
        setSessionAuthenticationStrategy(new NullAuthenticatedSessionStrategy());
        this.auth = auth;
        this.setAuthenticationSuccessHandler(successHandler);
        this.setAuthenticationFailureHandler(failureHandler);
    }


    @Override
    public void afterPropertiesSet() {
        Assert.notNull(getAuthenticationManager(), "authenticationManager must be specified");
        Assert.notNull(getSuccessHandler(), "AuthenticationSuccessHandler must be specified");
        Assert.notNull(getFailureHandler(), "AuthenticationFailureHandler must be specified");
    }

    /**
     * 账号密码 使用spring security默认的方法实现
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return Authentication
     * @throws AuthenticationException e
     */
    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        Authentication result;
        String body = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
        // 获取参数中的账号密码
        if (StringUtils.hasText(body)) {
            LinkedHashMap<?, ?> map = JSONUtils.parseObject(body);
            username = (String) map.get("account");
            password = (String) map.get("password");
        }

        if (!StringUtils.hasText(username)) {
            throw new BadCredentialsException("请输入账号");
        }
        if (!StringUtils.hasText(password)) {
            throw new BadCredentialsException("请输入密码");
        }
        username = username.trim();

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = auth.getConstructor(Object.class, Object.class).newInstance(username, password);

        try {
            result = this.getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);
        } catch (BadCredentialsException b) {
            throw new BadCredentialsException("密码错误");
        } catch (LockedException l) {
            throw new BadCredentialsException("账号已被冻结");
        }
        return result;
    }

}
