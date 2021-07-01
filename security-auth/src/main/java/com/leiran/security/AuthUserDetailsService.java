package com.leiran.security;

import com.leiran.common.bean.wechat.dto.WechatResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 自定义的登录方法，提供账号、手机号码、微信openId,unionID
 */
public interface AuthUserDetailsService extends UserDetailsService {
    /**
     * 账号登录
     *
     * @param account 账号
     * @return 用户信息
     */
    AuthUserDetails loadUserByAccount(String account);

    /**
     * 手机号码登录
     *
     * @param phoneNumber 手机号码
     * @return 用户信息
     */
    AuthUserDetails loadUserByPhoneNumber(String phoneNumber);

    /**
     * @param openId  小程序下openId
     * @param decrypt
     * @return 用户信息
     * <p>
     * 微信小程序登陆
     */
    AuthUserDetails loadUserByMiniOpenId(String unionId, String openId, WechatResponse decrypt);

    /**
     * @param openId 公众号下openId
     * @return 用户信息
     */
    AuthUserDetails loadUserByOfficialOpenId(String unionId, String openId);

    /**
     * @param unionId 微信unionId
     * @return 用户信息
     */
    AuthUserDetails loadUserByUnionId(String unionId, String openId);

    /**
     * @param id Id
     * @return 用户信息
     */
    AuthUserDetails loadUserById(String id);
}
