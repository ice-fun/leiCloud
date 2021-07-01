package com.leiran.security.configurer;

import com.leiran.security.filter.JwtAuthenticationFilter;
import com.leiran.security.token.JwtAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import java.util.ArrayList;
import java.util.List;

public class JwtAuthorizeConfigurer<T extends JwtAuthorizeConfigurer<T, B>, B extends HttpSecurityBuilder<B>> extends AbstractHttpConfigurer<T, B> {
    private final List<JwtAuthenticationFilter> filterList;

    public JwtAuthorizeConfigurer() {
        filterList = new ArrayList<>();
    }

    @Override
    public void configure(B builder) {
        for (JwtAuthenticationFilter jwtAuthenticationFilter : filterList) {
            jwtAuthenticationFilter.setAuthenticationManager(builder.getSharedObject(AuthenticationManager.class));
            JwtAuthenticationFilter filter1 = postProcess(jwtAuthenticationFilter);
            builder.addFilterBefore(filter1, LogoutFilter.class);
        }
    }

    public JwtAuthorizeConfigurer<T, B> addJwtAuthenticationFilter(JwtAuthenticationFilter jwtAuthenticationFilter) {
        filterList.add(jwtAuthenticationFilter);
        return this;
    }

    public JwtAuthorizeConfigurer<T, B> addJwtAuthenticationFilter(String path, Class<? extends JwtAuthenticationToken> auth, AuthenticationSuccessHandler successHandler, AuthenticationFailureHandler failureHandler, String... urls) {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(path, auth, successHandler, failureHandler, urls);
        filterList.add(filter);
        return this;
    }

}
