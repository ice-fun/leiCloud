package com.leiran.article.handler;

import com.leiran.common.common.Result;
import com.leiran.common.exception.CustomFailureException;
import io.lettuce.core.RedisCommandTimeoutException;
import io.lettuce.core.RedisException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 对方法参数校验异常处理方法(前端提交的方式为json格式出现异常时会被该异常类处理)
     * json格式提交时，spring会采用json数据的数据转换器进行处理（进行参数校验时错误是抛出MethodArgumentNotValidException异常）
     */
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handlerArgumentNotValidException(MethodArgumentNotValidException exception) {
        return Result.failure(exception.getBindingResult().getFieldError().getDefaultMessage());
    }


    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    public Result handlerIllegalArgumentException(IllegalArgumentException exception) {
        return Result.failure(exception.getLocalizedMessage());
    }

    @ResponseBody
    @ExceptionHandler(CustomFailureException.class)
    public Result handlerCustomFailureException(CustomFailureException exception) {
        return Result.failure(exception.getLocalizedMessage());
    }

    @ResponseBody
    @ExceptionHandler(RedisCommandTimeoutException.class)
    public Result handlerRedisCommandTimeoutException(RedisException exception) {
        return Result.failure("网络不太稳定，请重试");
    }


}
