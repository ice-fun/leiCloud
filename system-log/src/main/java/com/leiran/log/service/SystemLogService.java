package com.leiran.log.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leiran.common.bean.system.po.SystemLog;
import com.leiran.log.mapper.SystemLogMapper;
import com.leiran.log.message.IMessageQueue;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SystemLogService extends ServiceImpl<SystemLogMapper, SystemLog> implements IMessageQueue {

    @Resource
    StreamBridge streamBridge;

    /**
     * 最终的保存方法 可根据实际需求存储在数据库或者elasticsearch或者其他服务上
     * <p>
     * 如果是单机数据库，也就是各个微服务和log的存在一个数据库中，
     * 直接{@code save() }就可。
     * <p>
     * 存储在elasticsearch也可以如此。
     * <p>
     * 但如果是服务的数据库 和 日志的数据库是分开的，
     * 或者是想实现解耦， 方便调整
     * 那么就需要使用消息队列。
     *
     * @param systemLog 日志
     * @return result
     */
    public void saveLog(SystemLog systemLog) {
//        return this.save(systemLog);
        send(systemLog);
    }

    @Override
    public void send(SystemLog systemLog) {
        streamBridge.send("logms-in-0", systemLog);
    }
}
