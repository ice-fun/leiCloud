package com.leiran.security.param;

import lombok.Data;

/**
 * @author leiran
 * @Date 2020/12/11
 **/

@Data
public class MiniLoginParam {
    private String code;
    private String encryptedData;
    private String iv;
    private String inviterId;
    private WechatInfo wechatInfo;

    @Data
    public static class WechatInfo {
        private String encryptedData;
        private String iv;
        private String rawData;
        private UserInfo userInfo;
    }

    @Data
    public static class UserInfo {
        private String nickName;
        private String gender;
        private String city;
        private String province;
        private String country;
        private String avatarUrl;
    }
}
