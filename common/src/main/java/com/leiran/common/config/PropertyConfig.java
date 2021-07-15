package com.leiran.common.config;


//@SuppressWarnings("all")
public class PropertyConfig {


    //小程序
    public final static String MINI_PROGRAM_ID = "";
    public final static String MINI_PROGRAM_SECRET = "";

    //腾讯云短信
    public final static Integer TENCENT_MSG_APP_ID = 0;
    public final static String TENCENT_MSG_APP_KEY = "";


    //公众号
    public final static String WECHAT_OFFICIAL_ACCOUNTS_APP_ID = "";
    public final static String WECHAT_OFFICIAL_ACCOUNTS_APP_SECRET = "";
    public final static String WECHAT_OFFICIAL_ACCOUNTS_TOKEN = "";//
    public final static String WECHAT_OFFICIAL_ACCOUNTS_ASE_KEY = "";//


    //角色权限
    public final static String ROLE_ADMIN = "ROLE_ADMIN";  // 超级管理员
    public final static String ROLE_USER = "ROLE_USER";  // 用户
    public final static String ROLE_VISITOR = "ROLE_VISITOR"; // 游客


    public final static String DEFAULT_PASSWORD = "abc123321...";


    public final static Integer BOOL_TRUE = 1;
    public final static Integer BOOL_FALSE = 0;

    public static boolean TRUE(Integer value) {
        return BOOL_TRUE.equals(value);
    }

    public static boolean FALSE(Integer value) {
        return !TRUE(value);
    }
}

