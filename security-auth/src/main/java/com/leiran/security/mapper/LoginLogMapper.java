package com.leiran.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leiran.security.log.LoginLog;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface LoginLogMapper extends BaseMapper<LoginLog> {
}