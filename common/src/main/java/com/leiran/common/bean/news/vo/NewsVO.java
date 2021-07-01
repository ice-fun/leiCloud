package com.leiran.common.bean.news.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewsVO {
    private String newsId;

    private Long pageNo;
    private Long pageSize;

}
