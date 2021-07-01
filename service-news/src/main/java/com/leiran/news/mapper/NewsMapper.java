package com.leiran.news.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leiran.common.bean.news.po.News;
import org.apache.ibatis.annotations.Mapper;

/**
 * author：lei ran
 */

@Mapper
public interface NewsMapper extends BaseMapper<News> {
}
