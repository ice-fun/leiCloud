package com.leiran.security;

import com.leiran.security.handler.JsonResponseHandler;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyAuthenticationEntryPoint extends JsonResponseHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) {
        sendResponse(httpServletResponse, 404, e.getLocalizedMessage(), null);
    }
}
