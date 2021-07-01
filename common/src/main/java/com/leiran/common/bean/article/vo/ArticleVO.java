package com.leiran.common.bean.article.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * author：lei ran
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleVO {
    private String projectId;

    private Long pageNo;
    private Long pageSize;

}
