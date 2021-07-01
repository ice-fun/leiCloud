package com.leiran.security.filter;

import com.leiran.common.common.Assert;
import com.leiran.common.utils.JSONUtils;
import com.leiran.security.log.LoginLogService;
import com.leiran.security.param.WxLoginParam;
import com.leiran.security.token.WeChatAuthenticationToken;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;


public class WeChatAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private final Class<? extends WeChatAuthenticationToken> authClass;


    @Resource
    private LoginLogService loginLogService;


    public WeChatAuthenticationFilter(String loginPattern, Class<? extends WeChatAuthenticationToken> authClass) {
        super(new AntPathRequestMatcher(loginPattern, "POST"));
        setSessionAuthenticationStrategy(new NullAuthenticatedSessionStrategy());
        this.authClass = authClass;
    }

    public WeChatAuthenticationFilter(String loginPattern, Class<? extends WeChatAuthenticationToken> authClass,
                                      AuthenticationSuccessHandler successHandler, AuthenticationFailureHandler failureHandler) {
        super(new AntPathRequestMatcher(loginPattern, "POST"));
        setSessionAuthenticationStrategy(new NullAuthenticatedSessionStrategy());
        this.authClass = authClass;
        this.setAuthenticationSuccessHandler(successHandler);
        this.setAuthenticationFailureHandler(failureHandler);
    }

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(getSuccessHandler(), "AuthenticationSuccessHandler must be specified");
        Assert.notNull(getFailureHandler(), "AuthenticationFailureHandler must be specified");
        Assert.notNull(getAuthenticationManager(), "authenticationManager must be specified");
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        String body = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
        WxLoginParam wxLoginParam = new WxLoginParam();
        if (StringUtils.hasText(body)) {
            wxLoginParam = JSONUtils.parseObject(body, WxLoginParam.class);
        }
        WeChatAuthenticationToken authRequest = authClass.getConstructor(WxLoginParam.class).newInstance(wxLoginParam);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

}
