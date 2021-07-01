package com.leiran.security.util.wechat;


import com.leiran.common.bean.wechat.dto.WechatResponse;
import com.leiran.common.config.PropertyConfig;
import com.leiran.common.utils.JSONUtils;
import com.leiran.security.util.wechat.aes.AesException;
import com.leiran.security.util.wechat.aes.MiniDataCrypt;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MiniProgramLoginUtil {

    /**
     * 小程序授权登录
     *
     * @param code
     * @param id
     * @param secret
     * @return
     */
    private static WechatResponse miniProgramAuthorization(String code, String id, String secret) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        Map<String, String> params = new HashMap<String, String>();
        params.put("APPID", id);
        params.put("SECRET", secret);
        params.put("CODE", code);
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid={APPID}&secret={SECRET}&js_code={CODE}&grant_type=authorization_code";
        String response = restTemplate.getForObject(url, String.class, params);
        return JSONUtils.parseObject(response, WechatResponse.class);
    }

    public static WechatResponse userMiniProgramAuthorization(String code) {
        return miniProgramAuthorization(code, PropertyConfig.MINI_PROGRAM_ID, PropertyConfig.MINI_PROGRAM_SECRET);
    }


    public static WechatResponse decrypt(String info, String encodingAesKey) {
        LinkedHashMap<?, ?> object = JSONUtils.parseObject(info);
        String encryptedData = (String) object.get("encryptedData");
        String iv = (String) object.get("iv");
        return decrypt(encryptedData, iv, encodingAesKey);
    }


    public static WechatResponse decrypt(String encryptedData, String iv, String encodingAesKey) {
        try {
            return MiniDataCrypt.miniDecrypt(encryptedData, encodingAesKey, iv);
        } catch (AesException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] InputStreamToByte(InputStream in) throws IOException {
        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int ch;
        while ((ch = in.read(buffer)) != -1) {
            bytestream.write(buffer, 0, ch);
        }
        byte[] data = bytestream.toByteArray();
        bytestream.close();
        return data;
    }
}
