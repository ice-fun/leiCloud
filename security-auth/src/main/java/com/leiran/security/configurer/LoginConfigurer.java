package com.leiran.security.configurer;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import java.util.ArrayList;
import java.util.List;

public class LoginConfigurer<T extends LoginConfigurer<T, B>, B extends HttpSecurityBuilder<B>> extends AbstractHttpConfigurer<T, B> {
    private final List<AbstractAuthenticationProcessingFilter> filterList;

    public LoginConfigurer() {
        filterList = new ArrayList<>();
    }

    public LoginConfigurer(List<AbstractAuthenticationProcessingFilter> list) {
        filterList = list;
    }

    @Override
    public void configure(B builder) {
        for (AbstractAuthenticationProcessingFilter filter : filterList) {
            filter.setAuthenticationManager(builder.getSharedObject(AuthenticationManager.class));
            AbstractAuthenticationProcessingFilter filter1 = postProcess(filter);
            builder.addFilterAfter(filter1, LogoutFilter.class);
        }
    }

    public LoginConfigurer<T, B> addFilter(AbstractAuthenticationProcessingFilter filter) {
        filterList.add(filter);
        return this;
    }
}
