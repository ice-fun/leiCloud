package com.leiran.security.provider;

import com.leiran.security.AuthUserDetails;
import com.leiran.security.AuthUserDetailsService;
import com.leiran.security.param.VerifyParam;
import com.leiran.security.token.VerifyCodeAuthenticationToken;
import com.leiran.security.util.RedisUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.Collection;


public class VerifyCodeAuthenticationProvider extends AbstractMatchClassAuthenticationProvider {
    @Getter
    @Setter
    private RedisUtils redisUtils;

    public VerifyCodeAuthenticationProvider(AuthUserDetailsService userDetailsService, Class<? extends VerifyCodeAuthenticationToken> matchClass, RedisUtils redisUtils) {
        super(userDetailsService, matchClass);
        this.redisUtils = redisUtils;
    }

    @SneakyThrows
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        VerifyCodeAuthenticationToken verifyCodeAuthenticationToken = (VerifyCodeAuthenticationToken) authentication;
        VerifyParam verifyParam = verifyCodeAuthenticationToken.getVerifyParam();
        String userPhone = verifyParam.getUserPhone().trim();
        String verifyCode = verifyParam.getVerifyCode();

        if (StringUtils.isBlank(userPhone)) {
            throw new BadCredentialsException("手机号码不可为空");
        }
        if (StringUtils.isBlank(verifyCode)) {
            throw new BadCredentialsException("验证码不可为空");
        }

        String codeInRedis = (String) redisUtils.get(userPhone + "_vc");
        if (!verifyCode.equals(codeInRedis)) {
            throw new BadCredentialsException("验证码不正确");
        }

        AuthUserDetails userDetails = userDetailsService.loadUserByPhoneNumber(userPhone);
        checkAuthUserDetails(userDetails);


        return matchClass.getConstructor(Collection.class, AuthUserDetails.class, VerifyParam.class, Boolean.class)
                .newInstance(userDetails.getAuthorities(), userDetails, verifyParam, true);
    }
}
