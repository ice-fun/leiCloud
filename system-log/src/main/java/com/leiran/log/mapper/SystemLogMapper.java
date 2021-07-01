package com.leiran.log.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leiran.common.bean.system.po.SystemLog;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface SystemLogMapper extends BaseMapper<SystemLog> {
}