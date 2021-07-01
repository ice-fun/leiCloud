package com.leiran.security.handler.success;

import com.leiran.common.utils.BeanKit;
import com.leiran.common.utils.JwtTokenUtils;
import com.leiran.common.utils.RandomStringUtils;
import com.leiran.security.account.Admin;
import com.leiran.security.account.AdminService;
import com.leiran.security.account.AdminVO;
import com.leiran.security.handler.AbstractLoginSuccessHandler;
import com.leiran.security.param.MiniLoginParam;
import com.leiran.security.token.MiniProgramAuthenticationToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@Component
public class AdminMiniProgramLoginSuccessHandler extends AbstractLoginSuccessHandler {

    @Resource
    private AdminService adminService;

    @Override
    public Object preAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        MiniProgramAuthenticationToken miniProgramAuthenticationToken = (MiniProgramAuthenticationToken) authentication;
        MiniLoginParam miniLoginParam = miniProgramAuthenticationToken.getMiniLoginParam();
        Admin admin = (Admin) authentication.getPrincipal();
        if (StringUtils.isBlank(admin.getNickname())) {
            admin.setNickname("user" + RandomStringUtils.randomString(6));
        }
        admin.setTokenVersion(admin.getTokenVersion() + 1);
        adminService.saveOrUpdate(admin);
        Map<String, Object> claims = new HashMap<>();
        claims.put("tokenVersion", admin.getTokenVersion());
        String token = JwtTokenUtils.generateToken(claims, admin.getId());
        AdminVO adminVO = BeanKit.copy(admin, AdminVO.class);
        adminVO.setToken(token);
        return adminVO;
    }
}