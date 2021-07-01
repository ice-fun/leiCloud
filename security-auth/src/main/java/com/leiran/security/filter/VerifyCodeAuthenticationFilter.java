package com.leiran.security.filter;

import com.leiran.common.common.Assert;
import com.leiran.common.utils.JSONUtils;
import com.leiran.security.param.VerifyParam;
import com.leiran.security.token.VerifyCodeAuthenticationToken;
import lombok.SneakyThrows;
import org.springframework.security.authentication.BadCredentialsException;
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


public class VerifyCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private Class<? extends VerifyCodeAuthenticationToken> authClass;

    public VerifyCodeAuthenticationFilter(String loginPattern, Class<? extends VerifyCodeAuthenticationToken> authClass) {
        super(new AntPathRequestMatcher(loginPattern, "POST"));
        setSessionAuthenticationStrategy(new NullAuthenticatedSessionStrategy());
        this.authClass = authClass;
    }

    public VerifyCodeAuthenticationFilter(String loginPattern, Class<? extends VerifyCodeAuthenticationToken> authClass,
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
        VerifyParam verifyParam = new VerifyParam();
        if (StringUtils.hasText(body)) {
            verifyParam = JSONUtils.parseObject(body, VerifyParam.class);
        }
        if (verifyParam == null || !StringUtils.hasText(verifyParam.getUserPhone()) || !StringUtils.hasText(verifyParam.getVerifyCode())) {
            throw new BadCredentialsException("缺少参数");
        }

        VerifyCodeAuthenticationToken authRequest = authClass.getConstructor(VerifyParam.class).newInstance(verifyParam);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

}
