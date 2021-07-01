package com.leiran.article.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leiran.article.mapper.ArticleMapper;
import com.leiran.common.bean.article.po.Article;
import org.springframework.stereotype.Service;


@Service
public class ArticleService extends ServiceImpl<ArticleMapper, Article> {
}