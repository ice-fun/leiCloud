package com.leiran.security.provider;

import com.leiran.common.bean.wechat.dto.WechatResponse;
import com.leiran.common.common.Result;
import com.leiran.common.utils.JSONUtils;
import com.leiran.security.AuthUserDetails;
import com.leiran.security.AuthUserDetailsService;
import com.leiran.security.exception.AuthException;
import com.leiran.security.log.LoginLog;
import com.leiran.security.log.LoginLogService;
import com.leiran.security.param.MiniLoginParam;
import com.leiran.security.token.MiniProgramAuthenticationToken;
import com.leiran.security.util.wechat.MiniProgramLoginUtil;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.time.LocalDateTime;
import java.util.Collection;


public class MiniProgramAuthenticationProvider extends AbstractMatchClassAuthenticationProvider {

    private final LoginLogService loginLogService;

    public MiniProgramAuthenticationProvider(AuthUserDetailsService userDetailsService, Class<? extends MiniProgramAuthenticationToken> matchClass, LoginLogService loginLogService) {
        super(userDetailsService, matchClass);
        this.loginLogService = loginLogService;
    }

    @SneakyThrows
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        MiniProgramAuthenticationToken miniProgramAuthenticationToken = (MiniProgramAuthenticationToken) authentication;

        MiniLoginParam miniLoginParam = miniProgramAuthenticationToken.getMiniLoginParam();

        WechatResponse wechatResponse = MiniProgramLoginUtil.userMiniProgramAuthorization(miniLoginParam.getCode());

        if (wechatResponse == null || wechatResponse.getErrorCode() != null) {
            logException(miniProgramAuthenticationToken, wechatResponse, null);
            throw new AuthException(Result.WECHAT_AUTH_FAIL, "请求异常");
        }
        String openId = wechatResponse.getOpenId();
        String unionId = wechatResponse.getUnionId();
        String sessionKey = wechatResponse.getSessionKey();
        WechatResponse decrypt = null;
        try {
            decrypt = MiniProgramLoginUtil.decrypt(miniLoginParam.getEncryptedData(), miniLoginParam.getIv(), sessionKey);
            if (decrypt == null || StringUtils.isBlank(decrypt.getPurePhoneNumber())) {
                logException(miniProgramAuthenticationToken, decrypt, null);
                throw new BadCredentialsException("登录失败，请重试");
            }
            AuthUserDetails authUserDetails = userDetailsService.loadUserByMiniOpenId(unionId, openId, decrypt);
            checkAuthUserDetails(authUserDetails);
            return matchClass.getConstructor(Collection.class, AuthUserDetails.class, MiniLoginParam.class)
                    .newInstance(authUserDetails.getAuthorities(), authUserDetails, miniLoginParam);
        } catch (Exception e) {
            logException(miniProgramAuthenticationToken, decrypt, e);
            throw new BadCredentialsException("登录失败，请重试");
        }
    }

    /**
     * 这一步 异常的情况才记录
     *
     * @param miniProgramAuthenticationToken token
     * @param wechatResponse                 wechatResponse
     */
    private void logException(MiniProgramAuthenticationToken miniProgramAuthenticationToken, WechatResponse wechatResponse, Exception exception) {
        LoginLog loginLog = new LoginLog();
        loginLog.setLoginResult("授权异常");
        if (wechatResponse != null) {
            loginLog.setException(wechatResponse.getErrormsg());
        }
        if (exception != null) {
            loginLog.setException(exception.getMessage());
        }
        loginLog.setCreateTime(LocalDateTime.now());
        loginLog.setParam(JSONUtils.toJSONString(miniProgramAuthenticationToken));
        loginLog.setLoginPath("user/login/miniProgram");
        loginLog.setLogPoint("Provider");
        loginLogService.save(loginLog);
    }
}
