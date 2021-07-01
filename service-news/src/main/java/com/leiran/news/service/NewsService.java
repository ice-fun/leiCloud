package com.leiran.news.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leiran.common.bean.news.po.News;
import com.leiran.news.mapper.NewsMapper;
import org.springframework.stereotype.Service;

/**
 * author：lei ran
 */

@Service
public class NewsService extends ServiceImpl<NewsMapper, News> {
}
