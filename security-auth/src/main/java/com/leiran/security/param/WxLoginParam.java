package com.leiran.security.param;

import lombok.Data;

/**
 * @author leiran
 * @Date 2021/1/11
 **/

@Data
public class WxLoginParam {
    private String userPhone;
    private String verifyCode;
    private String code;
    private String rawData;
}
