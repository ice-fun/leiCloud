package com.leiran.security.util.wechat;

import com.leiran.common.bean.wechat.dto.WechatResponse;
import com.leiran.common.bean.wechat.dto.WechatUserInfo;
import com.leiran.common.config.PropertyConfig;
import com.leiran.common.utils.JSONUtils;
import com.leiran.security.account.User;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author leiran
 **/

public class OfficialLoginUtils {
    /**
     * 微信公众号授权方法
     *
     * @param code 微信拉取授权窗口后 返回的CODE， 前端传递
     * @return
     */
    public static WechatResponse authorization(String code) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        Map<String, String> params = new HashMap<String, String>();
        params.put("APPID", PropertyConfig.WECHAT_OFFICIAL_ACCOUNTS_APP_ID);
        params.put("SECRET", PropertyConfig.WECHAT_OFFICIAL_ACCOUNTS_APP_SECRET);
        params.put("CODE", code);
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={APPID}&secret={SECRET}&code={CODE}&grant_type=authorization_code";
        String response = restTemplate.getForObject(url, String.class, params);
        return JSONUtils.parseObject(response, WechatResponse.class);
    }

    /**
     * 拉取个人信息
     *
     * @param accessToken 上一步，授权成功后，微信返回
     * @param openId      授权成功后微信返回
     * @return
     */
    private static WechatUserInfo getUserInformation(String accessToken, String openId) {

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        Map<String, String> params = new HashMap<String, String>();
        params.put("ACCESS_TOKEN", accessToken);
        params.put("OPENID", openId);
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token={ACCESS_TOKEN}&openid={OPENID}&lang=zh_CN";
        String response = restTemplate.getForObject(url, String.class, params);
        return JSONUtils.parseObject(response, WechatUserInfo.class);
    }


    public static User getUserInfo(String accessToken, String openId) {
        WechatUserInfo wechatUserInfo = getUserInformation(accessToken, openId);
        User user = new User();
        user.setWechatUnionId(wechatUserInfo.getUnionId());
        return user;
    }
}
