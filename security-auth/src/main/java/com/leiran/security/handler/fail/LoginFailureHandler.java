package com.leiran.security.handler.fail;

import com.leiran.common.common.Result;
import com.leiran.security.exception.AuthException;
import com.leiran.security.handler.JsonResponseHandler;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class LoginFailureHandler extends JsonResponseHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) {
        Result<?> result = Result.base(Result.LOGIN_FAIL_CODE, e.getMessage(), null);
        if (e instanceof AuthException) {
            AuthException authException = (AuthException) e;
            Result<?> result1 = authException.getResult();
            result = result1 == null ? result : result1;
        }
        sendResponse(httpServletResponse, result);
    }
}
