package com.leiran.security.handler.success;


import com.leiran.common.common.Result;
import com.leiran.common.config.PropertyConfig;
import com.leiran.common.utils.BeanKit;
import com.leiran.common.utils.IpUtil;
import com.leiran.common.utils.JwtTokenUtils;
import com.leiran.security.account.User;
import com.leiran.security.account.UserService;
import com.leiran.security.account.UserVO;
import com.leiran.security.handler.AbstractLoginSuccessHandler;
import com.leiran.security.log.LoginLogService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import static com.leiran.common.common.Result.NO_ACCESS;


@Component
public class UserPasswordLoginSuccessHandler extends AbstractLoginSuccessHandler {

    @Resource
    private UserService userService;

    @Resource
    private LoginLogService loginLogService;

    @Override
    public Object preAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        if (!user.getRole().equals(PropertyConfig.ROLE_USER)) {
            sendResponse(httpServletResponse, Result.base(NO_ACCESS, "无权访问"));
            return null;
        }
        user.setTokenVersion(user.getTokenVersion() + 1);
        userService.updateById(user);
        Map<String, Object> claims = new HashMap<>();
        claims.put("tokenVersion", user.getTokenVersion());
        String token = JwtTokenUtils.generateToken(claims, user.getId());
        UserVO userVO = BeanKit.copy(user, UserVO.class);
        userVO.setToken(token);
        // 记录登录成功日志
        loginLogService.saveSuccessLog(user.getUserId(), user.getNickname(), "user/login/account", "登录成功", IpUtil.getIpAddress(httpServletRequest), "{}", "successHandler");
        return userVO;
    }
}
