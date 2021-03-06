package com.leiran.security.handler.fail;

import com.leiran.common.common.Result;
import com.leiran.security.handler.JsonResponseHandler;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenAuthenticationFailureHandler extends JsonResponseHandler implements org.springframework.security.web.authentication.AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) {
        sendResponse(httpServletResponse, Result.TOKEN_AUTH_FAIL, e.getMessage(), null);
    }
}
