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
public class CodeLoginFailureHandler extends JsonResponseHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) {
        Result<?> result = Result.base(Result.FAIL_CODE, e.getMessage(), null);
        if (e instanceof AuthException) {
            AuthException authException = (AuthException) e;
            result = authException.getResult() == null ? result : authException.getResult();
        }
        sendResponse(httpServletResponse, result);
    }
}
