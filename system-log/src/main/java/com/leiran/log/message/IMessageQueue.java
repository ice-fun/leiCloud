package com.leiran.log.message;

import com.leiran.common.bean.system.po.SystemLog;


public interface IMessageQueue {
    void send(SystemLog systemLog);
}
