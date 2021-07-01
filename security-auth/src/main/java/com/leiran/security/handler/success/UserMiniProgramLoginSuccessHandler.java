package com.leiran.security.handler.success;

import com.leiran.common.utils.*;
import com.leiran.security.account.User;
import com.leiran.security.account.UserService;
import com.leiran.security.account.UserVO;
import com.leiran.security.handler.AbstractLoginSuccessHandler;
import com.leiran.security.log.LoginLogService;
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
public class UserMiniProgramLoginSuccessHandler extends AbstractLoginSuccessHandler {

    @Resource
    private UserService userService;

    @Resource
    private LoginLogService loginLogService;

    @Override
    public Object preAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        MiniProgramAuthenticationToken miniProgramAuthenticationToken = (MiniProgramAuthenticationToken) authentication;
        MiniLoginParam miniLoginParam = miniProgramAuthenticationToken.getMiniLoginParam();
        User user = (User) authentication.getPrincipal();
        if (StringUtils.isBlank(user.getNickname())) {
            user.setNickname("user" + RandomStringUtils.randomString(6));
        }
        Map<String, Object> claims = new HashMap<>();
        user.setTokenVersion(user.getTokenVersion() + 1);

        userService.saveOrUpdate(user);
        claims.put("tokenVersion", user.getTokenVersion());
        String token = JwtTokenUtils.generateToken(claims, user.getId());
        UserVO userVo = BeanKit.copy(user, UserVO.class);
        userVo.setToken(token);
        // 记录登录成功日志
        loginLogService.saveSuccessLog(user.getUserId(), user.getNickname(), "user/login/miniProgram", "登录成功", IpUtil.getIpAddress(httpServletRequest), JSONUtils.toJSONString(miniLoginParam), "successHandler");

        return userVo;
    }
}