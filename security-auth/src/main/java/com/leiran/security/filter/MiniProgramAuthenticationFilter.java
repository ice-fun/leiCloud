package com.leiran.security.filter;

import com.leiran.common.common.Assert;
import com.leiran.common.utils.JSONUtils;
import com.leiran.security.param.MiniLoginParam;
import com.leiran.security.token.MiniProgramAuthenticationToken;
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


public class MiniProgramAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private final Class<? extends MiniProgramAuthenticationToken> authClass;

    public MiniProgramAuthenticationFilter(String loginPattern, Class<? extends MiniProgramAuthenticationToken> authClass) {
        super(new AntPathRequestMatcher(loginPattern, "POST"));
        setSessionAuthenticationStrategy(new NullAuthenticatedSessionStrategy());
        this.authClass = authClass;
    }

    public MiniProgramAuthenticationFilter(String loginPattern, Class<? extends MiniProgramAuthenticationToken> authClass,
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
        MiniLoginParam miniLoginParam = new MiniLoginParam();
        if (StringUtils.hasText(body)) {
            miniLoginParam = JSONUtils.parseObject(body, MiniLoginParam.class);
        }
        if (miniLoginParam == null || !StringUtils.hasText(miniLoginParam.getCode()) || !StringUtils.hasText(miniLoginParam.getEncryptedData()) || !StringUtils.hasText(miniLoginParam.getIv())) {
            throw new BadCredentialsException("缺少参数");
        }

        MiniProgramAuthenticationToken authRequest = authClass.getConstructor(MiniLoginParam.class).newInstance(miniLoginParam);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

}
