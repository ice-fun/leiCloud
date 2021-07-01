package com.leiran.news.controller;

import com.leiran.news.service.NewsService;
import com.leiran.security.util.RedisUtils;

import javax.annotation.Resource;

/**
 * author：lei ran
 */


public abstract class AbstractController {
    @Resource
    protected NewsService newsService;

    @Resource
    protected RedisUtils redisUtils;
}
