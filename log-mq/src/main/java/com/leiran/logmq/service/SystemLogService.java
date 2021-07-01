package com.leiran.logmq.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leiran.common.bean.system.po.SystemLog;
import com.leiran.logmq.mapper.SystemLogMapper;
import org.springframework.stereotype.Service;


@Service
public class SystemLogService extends ServiceImpl<SystemLogMapper, SystemLog> {
}