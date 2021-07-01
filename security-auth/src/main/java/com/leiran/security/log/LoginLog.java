package com.leiran.security.log;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginLog {
    @TableId
    private String loginLogId;
    private String loginPath;
    private String loginResult;
    private String logPoint;
    private String ip;
    private String userId;
    private String username;
    private String param;
    private String exception;
    @JsonIgnore
    private Integer isDelete;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    @JsonIgnore
    private LocalDateTime updateTime;
}
