package com.leiran.security.param;

import com.leiran.common.bean.wechat.dto.WechatResponse;
import com.leiran.common.bean.wechat.dto.WechatUserInfo;
import lombok.Data;

/**
 * @author leiran
 * @Date 2020/12/11
 **/

@Data
public class VerifyParam {
    private String userPhone;
    private String verifyCode;
    private String code;
    private String openid;
    private String inviterId;
    private String appleId;
    private WechatResponse wechatResponse;
    private WechatUserInfo wechatUserInfo;
}
