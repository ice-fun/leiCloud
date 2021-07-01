package com.leiran.security.handler.fail;

import com.leiran.common.common.Result;
import com.leiran.security.handler.JsonResponseHandler;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RestAuthenticationAccessDeniedHandler extends JsonResponseHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        //登陆状态下，权限不足执行该方法
        sendResponse(httpServletResponse, Result.NO_ACCESS, "无权访问", null);
    }
}
