package com.leiran.common.bean.article.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * author：lei ran
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Article {
    @TableId
    private String articleId;
    @JsonIgnore
    private Integer isDelete;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    @JsonIgnore
    private LocalDateTime updateTime;
}
