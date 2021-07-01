package com.leiran.logmq.listener;

import com.leiran.common.bean.system.po.SystemLog;
import com.leiran.logmq.service.SystemLogService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.function.Consumer;


@Component
public class ReceiveMessageListener {

    @Resource
    SystemLogService systemLogService;

    /**
     * 这里的方法名 {@code logms} 需要和发送时binding的名字{@code logms-in-0} 前面一致
     *
     * @return result
     */
    @Bean
    Consumer<SystemLog> logms() {
        return this::saveLog;
    }

    /**
     * 这里是最终的存储日志的方法
     * 可自由选择存储到数据库 还是 elasticsearch
     * 或者是其他服务
     *
     * @param systemLog log
     */
    private void saveLog(SystemLog systemLog) {
        systemLogService.save(systemLog);
    }
}
