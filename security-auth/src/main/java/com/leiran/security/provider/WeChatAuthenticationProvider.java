package com.leiran.security.provider;

import com.leiran.common.bean.wechat.dto.WechatResponse;
import com.leiran.common.common.Result;
import com.leiran.security.AuthUserDetails;
import com.leiran.security.AuthUserDetailsService;
import com.leiran.security.account.User;
import com.leiran.security.exception.AuthException;
import com.leiran.security.param.WxLoginParam;
import com.leiran.security.token.WeChatAuthenticationToken;
import com.leiran.security.util.wechat.OfficialLoginUtils;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.Collection;


public class WeChatAuthenticationProvider extends AbstractMatchClassAuthenticationProvider {

    public WeChatAuthenticationProvider(AuthUserDetailsService userDetailsService, Class<? extends WeChatAuthenticationToken> matchClass) {
        super(userDetailsService, matchClass);
    }

    @SneakyThrows
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        WeChatAuthenticationToken weChatAuthenticationToken = (WeChatAuthenticationToken) authentication;
        WxLoginParam wxLoginParam = weChatAuthenticationToken.getWxLoginParam();
        if (wxLoginParam == null || StringUtils.isBlank(wxLoginParam.getCode())) {
            throw new AuthException("参数错误");
        }
        WechatResponse wechatResponse = OfficialLoginUtils.authorization(wxLoginParam.getCode());
        if (wechatResponse == null || wechatResponse.getErrorCode() != null) {
            throw new AuthException(Result.WECHAT_AUTH_FAIL, "请求异常");
        }

        AuthUserDetails authUserDetails = userDetailsService.loadUserByOfficialOpenId(wechatResponse.getUnionId(), wechatResponse.getOpenId());
        checkAuthUserDetails(authUserDetails);
        User userInfo = OfficialLoginUtils.getUserInfo(wechatResponse.getAccessToken(), wechatResponse.getOpenId());
        User user = (User) authUserDetails;
        user.setWechatUnionId(userInfo.getWechatUnionId());
        return matchClass.getConstructor(Collection.class, AuthUserDetails.class, WxLoginParam.class)
                .newInstance(authUserDetails.getAuthorities(), authUserDetails, wxLoginParam);
    }
}
