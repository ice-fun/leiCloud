package com.leiran.security.configurer;

import com.leiran.security.filter.WebSocketRequestFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import java.util.ArrayList;
import java.util.List;

public class WsAuthorizeConfigurer<T extends WsAuthorizeConfigurer<T, B>, B extends HttpSecurityBuilder<B>> extends AbstractHttpConfigurer<T, B> {
    private final List<WebSocketRequestFilter> filterList;

    public WsAuthorizeConfigurer() {
        filterList = new ArrayList<>();
    }

    @Override
    public void configure(B builder) {
        for (WebSocketRequestFilter socketRequestFilter : filterList) {
            socketRequestFilter.setAuthenticationManager(builder.getSharedObject(AuthenticationManager.class));
            WebSocketRequestFilter filter1 = postProcess(socketRequestFilter);
            builder.addFilterBefore(filter1, LogoutFilter.class);
        }
    }

    public WsAuthorizeConfigurer<T, B> addWsAuthenticationFilter(WebSocketRequestFilter webSocketRequestFilter) {
        filterList.add(webSocketRequestFilter);
        return this;
    }

}
