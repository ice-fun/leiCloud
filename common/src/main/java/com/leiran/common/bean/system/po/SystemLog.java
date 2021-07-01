package com.leiran.common.bean.system.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SystemLog {
    @TableId
    private String systemLogId;
    private String path;
    private String method;
    private String type;
    private String name;
    private String platform;
    private String ip;
    private String userId;
    private String username;
    private String header;
    private String param;
    private String result;
    private String exception;
    @JsonIgnore
    private Integer isDelete;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    @JsonIgnore
    private LocalDateTime updateTime;
}
