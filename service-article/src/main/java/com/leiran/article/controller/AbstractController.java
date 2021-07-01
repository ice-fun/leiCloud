package com.leiran.article.controller;

import com.leiran.article.service.ArticleService;
import com.leiran.security.account.UserService;

import javax.annotation.Resource;

public abstract class AbstractController {

    @Resource
    protected ArticleService articleService;

    @Resource
    protected UserService userService;

}
