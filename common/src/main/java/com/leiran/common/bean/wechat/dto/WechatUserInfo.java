package com.leiran.common.bean.wechat.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WechatUserInfo {
    private String subscribe;
    @JsonProperty("openid")
    private String openId;

    private String nickname;
    private Integer sex;

    private String language;
    private String city;
    private String province;
    private String country;
    @JsonProperty("headimgurl")
    private String headImgUrl;
    @JsonProperty("subscribe_time")
    private String subscribeTime;
    @JsonProperty("unionid")
    private String unionId;
    private String remark;
    @JsonProperty("groupid")
    private String groupId;
    @JsonProperty("tagid_list")
    private List tagIdList;
    @JsonProperty("subscribe_scene")
    private String subscribeScene;
    @JsonProperty("qr_scene")
    private Integer qrScene;
    @JsonProperty("qr_scene_str")
    private String qrSceneStr;


    @JsonProperty("errcode")
    private String errCode;
    @JsonProperty("errmsg")
    private Error errMsg;
}
