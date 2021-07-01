package com.leiran.common.bean.system.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdvertPictureVO {
    private String advertPictureId;
    private String advertCode;
    private String pictureUrl;
    private String towardUrl;
    private Integer advertStatus;

    private String keyword;
    private Long pageNo;
    private Long pageSize;
}
