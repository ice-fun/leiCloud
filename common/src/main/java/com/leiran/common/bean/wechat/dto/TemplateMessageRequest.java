package com.leiran.common.bean.wechat.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.LinkedHashMap;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TemplateMessageRequest {
    @JsonProperty("touser")
    private String toUser;
    @JsonProperty("template_id")
    private String templateId;
    @JsonProperty("url")
    private String url;
    @JsonProperty("miniprogram")
    private MiniProgramVo miniProgram;
    private LinkedHashMap<String, MessageParam> data;

    public void setAppId(String appId) {
        if (miniProgram == null) {
            miniProgram = new MiniProgramVo();
        }
        miniProgram.setAppId(appId);
    }

    public void setPathPage(String pathPage) {
        if (miniProgram == null) {
            miniProgram = new MiniProgramVo();
        }
        miniProgram.setPagePath(pathPage);
    }

    public void setValues(String[] values) {
        for (int i = 0; i < values.length; i++) {
            String value = values[i];
            MessageParam messageParam = new MessageParam();
            messageParam.setValue(value);
            if (i == 0) {
                data = new LinkedHashMap<>();
                data.put("first", messageParam);
            } else if (i == values.length - 1) {
                data.put("remark", messageParam);
            } else {
                data.put("keyword" + i, messageParam);
            }
        }
    }

    @Data
    public class MiniProgramVo {
        @JsonProperty("appid")
        private String appId;
        @JsonProperty("pagepath")
        private String pagePath;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public class MessageParam {
        private String value;
        private String color;
    }

}
