package com.leiran.common.bean.wechat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WechatResponse {
    @JsonProperty("openid")
    private String openId;
    private String nickName;
    private String gender;
    private String city;
    private String province;
    private String country;
    private String avatarUrl;
    @JsonProperty("unionid")
    private String unionId;
    @JsonProperty("session_key")
    private String sessionKey;
    @JsonProperty("errCode")
    private String errorCode;
    @JsonProperty("errmsg")
    private String errormsg;

    private WechatResponse userInfo;

    private String accessToken;

    private String phoneNumber;
    private String purePhoneNumber;
    private String countryCode;
    @JsonProperty("watermark")
    private Watermark watermark;
}
