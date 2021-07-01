package com.leiran.common.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 统一的响应结果集
 *
 * @param <T>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> implements Serializable {
    public final static int SUCCESS_CODE = 200; // 业务成功
    public final static String SUCCESS_MSG = "success";
    public final static int FAIL_CODE = 300; // 业务失败
    public final static int TOKEN_AUTH_FAIL = 301; // token校验失败
    public final static int UNAUTHORIZED_CODE = 301; // 未登录/未授权
    public final static int LOGIN_FAIL_CODE = 302; // 登录失败
    public final static int WECHAT_AUTH_FAIL = 303; // 微信请求异常
    public final static int NO_ACCESS = 305; // 权限不足
    public final static int ERROR_CODE = 500; // 服务器异常

    private int statusCode;
    private String statusMsg;
    private T data;

    public static <T> Result<T> base(int code, String msg, T data) {
        return new Result<T>(code, msg, data);
    }

    public static <T> Result<T> base(int code, String msg) {
        return base(code, msg, null);
    }

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> success(T data) {
        return base(SUCCESS_CODE, SUCCESS_MSG, data);
    }

    public static <T> Result<T> failure(String msg, T data) {
        return base(FAIL_CODE, msg, data);
    }

    public static <T> Result<T> failure(String msg) {
        return failure(msg, null);
    }

    public static <T> Result<T> condition(boolean condition, String failedMsg) {
        return condition(condition, failedMsg, null);
    }

    public static <T> Result<T> condition(boolean condition, String failedMsg, T data) {
        return condition ? success(data) : failure(failedMsg);
    }
}

