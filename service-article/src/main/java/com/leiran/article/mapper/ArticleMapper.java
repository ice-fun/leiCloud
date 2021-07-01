package com.leiran.article.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leiran.common.bean.article.po.Article;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
}