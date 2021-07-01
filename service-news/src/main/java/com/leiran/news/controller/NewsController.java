package com.leiran.news.controller;

import com.leiran.common.bean.news.po.News;
import com.leiran.common.common.Result;
import com.leiran.log.aop.AopLog;
import com.leiran.security.util.AllowVisitor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * author：lei ran
 */

@RestController
@RequestMapping("/user/news/")
public class NewsController extends AbstractController {

    @PostMapping("/news")
    @AllowVisitor
    @AopLog(name = "新闻列表", platform = "用户端")
    public Result news() {
        List<News> news = newsService.list();
        return Result.success(news);
    }

    @PostMapping("/news2")
    @AopLog(saveLog = false, name = "news2", platform = "用户端")
    public Result news2() {

        return Result.success();
    }
}
