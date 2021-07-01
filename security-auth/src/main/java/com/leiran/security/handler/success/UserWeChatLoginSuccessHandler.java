package com.leiran.security.handler.success;


import com.leiran.common.utils.BeanKit;
import com.leiran.common.utils.JwtTokenUtils;
import com.leiran.security.account.User;
import com.leiran.security.account.UserService;
import com.leiran.security.account.UserVO;
import com.leiran.security.handler.AbstractLoginSuccessHandler;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@Component
public class UserWeChatLoginSuccessHandler extends AbstractLoginSuccessHandler {

    @Resource
    private UserService userService;


    @Override
    public Object preAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        Map<String, Object> claims = new HashMap<>();
        user.setTokenVersion(user.getTokenVersion() + 1);
        userService.updateById(user);
        claims.put("tokenVersion", user.getTokenVersion());
        String token = JwtTokenUtils.generateToken(claims, user.getId());
        UserVO userVO = BeanKit.copy(user, UserVO.class);
        userVO.setToken(token);
        return userVO;
    }
}