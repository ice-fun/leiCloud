package com.leiran.security.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AbstractLoginSuccessHandler extends JsonResponseHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        Object data = preAuthenticationSuccess(httpServletRequest, httpServletResponse, authentication);
        if (!httpServletResponse.isCommitted()) {
            sendResponse(httpServletResponse, HttpServletResponse.SC_OK, "success", data);
        }
    }

    public abstract Object preAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                                    HttpServletResponse httpServletResponse,
                                                    Authentication authentication);


}
