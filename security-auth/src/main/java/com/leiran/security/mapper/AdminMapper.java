package com.leiran.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leiran.security.account.Admin;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface AdminMapper extends BaseMapper<Admin> {
}
