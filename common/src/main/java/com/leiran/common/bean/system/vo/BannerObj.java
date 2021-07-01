package com.leiran.common.bean.system.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BannerObj {
    private Integer code;
    private String pictureUrl;
    private String towardUrl;
}
