package com.leiran.security.handler;

import com.leiran.common.common.Result;
import com.leiran.common.utils.JSONUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class JsonResponseHandler {

    /**
     * 执行登录/鉴权 响应方法
     *
     * @param httpServletResponse response
     * @param code                业务状态码
     * @param msg                 状态描述
     * @param data                数据
     */
    protected void sendResponse(HttpServletResponse httpServletResponse, int code, String msg, Object data) {
        Result<?> responseObject = Result.base(code, msg, data);
        sendResponse(httpServletResponse, responseObject);
    }

    protected void sendResponse(HttpServletResponse httpServletResponse, Result<?> result) {
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.setCharacterEncoding("UTF-8");
        try (PrintWriter out = httpServletResponse.getWriter()) {
            out.append(JSONUtils.toJSONString(result));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
