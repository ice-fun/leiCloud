package com.leiran.security;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

/**
 * 自定义的用户鉴权信息接口，为了方便使用不同的登录方法，
 * 每个账号类的类都应该继承此接口
 */
public interface AuthUserDetails extends UserDetails {
    /**
     * @return token版本字段
     */
    Map<String, Integer> getTokenVersionMap();

    /**
     * @return 表id
     */
    String getId();

    /**
     * @return 用户昵称
     */
    String getName();

    /**
     * @return 微信小程序openid
     */
    String getMiniOpenId();

    /**
     * @return 微信公众号openid
     */
    String getOfficialOpenId();

    /**
     * @return 微信unionId
     */
    String getUnionId();

    /**
     * @return 角色
     */
    String getRole();

}
