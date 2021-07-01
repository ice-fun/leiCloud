package com.leiran.common.bean.wechat.po;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 关注公众号的用户
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OfficialSubscriber {
    @TableId
    private String subscriberId;
    private String officialOpenId;
    private String UnionId;
    @JsonIgnore
    private Integer isDelete;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    @JsonIgnore
    @TableField(updateStrategy = FieldStrategy.NEVER)
    private LocalDateTime updateTime;
}
