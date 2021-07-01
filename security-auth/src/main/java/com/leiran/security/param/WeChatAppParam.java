package com.leiran.security.param;

import com.leiran.common.bean.wechat.dto.WechatUserInfo;
import lombok.Data;

/**
 * @author leiran
 * @Date 2021/1/11
 **/

@Data
public class WeChatAppParam {
    private String openid;
    private String unionid;
    private String access_token;
    private String userPhone;
    private String verifyCode;
    private String nickname;
    private Integer userSex;
    private String city;
    private String province;
    private String country;
    private String userAvatar;
    private String inviterId;
    private WechatUserInfo wechatUserInfo;
}
