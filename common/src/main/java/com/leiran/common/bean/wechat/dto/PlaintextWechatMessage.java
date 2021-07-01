package com.leiran.common.bean.wechat.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

import static javax.xml.bind.annotation.XmlAccessType.FIELD;

@Data
@XmlRootElement(name = "xml")
@XmlAccessorType(FIELD)
public class PlaintextWechatMessage {
    @XmlElement(name = "ToUserName")
    private String toUserName;

    @XmlElement(name = "FromUserName")
    private String fromUserName;

    @XmlElement(name = "CreateTime")
    private Long createTime;

    @XmlElement(name = "MsgType")
    private String msgType;

    @XmlElement(name = "Event")
    private String event;

    @XmlElement(name = "EventKey")
    private String eventKey;

    @XmlElement(name = "Ticket")
    private String ticket;

    @XmlElement(name = "Latitude")
    private String latitude;

    @XmlElement(name = "Longitude")
    private String longitude;

    @XmlElement(name = "Content")
    private String content;

    @XmlElement(name = "MsgId")
    private String msgId;

    @XmlElementWrapper(name = "SubscribeMsgPopupEvent")
    @XmlElement(name = "List")
    private List<Event> subscribeMsgPopupEvent;


    @Data
    @XmlRootElement(name = "List")
    @XmlAccessorType(FIELD)
    public static class Event {
        @XmlElement(name = "TemplateId")
        private String templateId;
        @XmlElement(name = "SubscribeStatusString")
        private String subscribeStatusString;
        @XmlElement(name = "PopupScene")
        private String popupScene;
    }


}
