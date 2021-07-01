package com.leiran.security.handler.success;

import com.leiran.common.utils.BeanKit;
import com.leiran.common.utils.JwtTokenUtils;
import com.leiran.common.utils.RandomStringUtils;
import com.leiran.security.account.User;
import com.leiran.security.account.UserService;
import com.leiran.security.account.UserVO;
import com.leiran.security.handler.AbstractLoginSuccessHandler;
import com.leiran.security.param.VerifyParam;
import com.leiran.security.token.VerifyCodeAuthenticationToken;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserCodeLoginSuccessHandler extends AbstractLoginSuccessHandler {
    @Resource
    private UserService userService;

    @Override
    public Object preAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        VerifyCodeAuthenticationToken verifyCodeAuthenticationToken = (VerifyCodeAuthenticationToken) authentication;
        VerifyParam verifyParam = verifyCodeAuthenticationToken.getVerifyParam();
        User user = (User) authentication.getPrincipal();


        if (StringUtils.isBlank(user.getNickname())) {
            user.setNickname("user" + RandomStringUtils.randomString(6));
        }
        user.setTokenVersion(user.getTokenVersion() + 1);
        userService.saveOrUpdate(user);
        Map<String, Object> claims = new HashMap<>();
        claims.put("tokenVersion", user.getTokenVersion());
        String token = JwtTokenUtils.generateToken(claims, user.getId());
        UserVO userVo = BeanKit.copy(user, UserVO.class);
        userVo.setToken(token);
        return userVo;
    }
}
