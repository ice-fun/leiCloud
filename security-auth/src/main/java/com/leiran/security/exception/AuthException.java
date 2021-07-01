package com.leiran.security.exception;


import com.leiran.common.common.Result;
import org.springframework.security.core.AuthenticationException;

/**
 * @author lei ran
 * <p>
 * 可封装Result响应结果集的异常类
 * @see Result
 **/


public class AuthException extends AuthenticationException {
    private Result<?> result;

    public AuthException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public AuthException(String msg) {
        super(msg);
    }

    public AuthException(int code, String msg, Throwable cause) {
        super(msg, cause);
        result = Result.base(code, msg);
    }

    public AuthException(int code, String msg) {
        super(msg);
        result = Result.base(code, msg);
    }

    public AuthException(Result<?> result) {
        super(result.getStatusMsg());
        this.result = result;
    }

    public Result<?> getResult() {
        return result;
    }
}
