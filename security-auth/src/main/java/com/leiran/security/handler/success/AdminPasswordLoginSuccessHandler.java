package com.leiran.security.handler.success;

import com.leiran.common.common.Result;
import com.leiran.common.enums.RoleEnum;
import com.leiran.common.utils.BeanKit;
import com.leiran.common.utils.JwtTokenUtils;
import com.leiran.security.account.Admin;
import com.leiran.security.account.AdminService;
import com.leiran.security.account.AdminVO;
import com.leiran.security.handler.AbstractLoginSuccessHandler;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Component
public class AdminPasswordLoginSuccessHandler extends AbstractLoginSuccessHandler {
    @Resource
    private AdminService adminService;

    @Override
    public Object preAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        Admin admin = (Admin) authentication.getPrincipal();
        if (!RoleEnum.isAdmin(admin.getRole())) {
            sendResponse(httpServletResponse, Result.NO_ACCESS, "无权访问", null);
            return null;
        }
        admin.setTokenVersion(admin.getTokenVersion() + 1);
        adminService.updateById(admin);
        Map<String, Object> claims = new HashMap<>();
        claims.put("tokenVersion", admin.getTokenVersion());
        String token = JwtTokenUtils.generateToken(claims, admin.getId());
        AdminVO adminVO = BeanKit.copy(admin, AdminVO.class);
        adminVO.setToken(token);
        return adminVO;
    }
}
