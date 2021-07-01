package com.leiran.article.controller;

import com.leiran.common.bean.article.po.Article;
import com.leiran.common.bean.article.vo.ArticleVO;
import com.leiran.common.common.Result;
import com.leiran.common.utils.BeanKit;
import com.leiran.log.aop.AopLog;
import com.leiran.security.account.User;
import com.leiran.security.util.AllowVisitor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * author：lei ran
 */

@RestController
@RequestMapping("/user/article/")
public class ArticleController extends AbstractController {

    /**
     * 此方法添加了 {@code AopLog}注解，
     * 并且{@code saveLog}默认为{@code true}，会正常收集日志
     * 此方法添加了 {@code AllowVisitor}注解，用户未登录的时候，也可以访问到此接口。
     * <p>
     * spring security 的注解 {@code AuthenticationPrincipal}可以获取请求此接口的用户，前提是传入token
     * 无token的情况下，user为临时值，没有ID
     *
     * @param user user
     * @return result
     * @see com.leiran.security.provider.JwtAuthenticationProvider#authenticate(Authentication)
     * @see com.leiran.log.aop.AopLogAspect
     */
    @PostMapping("/articles")
    @AllowVisitor
    @AopLog(name = "文章列表", platform = "用户端")
    public Result articles(@AuthenticationPrincipal User user) {
        String userId = user.getUserId();
        List<Article> list = articleService.list();
        return Result.success(list);
    }

    /**
     * 此方法添加了 {@code AllowVisitor}注解，用户未登录的时候，也可以访问到此接口。
     * 但由于 {@code checkToken = false}，因此不经过jwt校验token，
     * spring security 的注解 {@code AuthenticationPrincipal} 的结果必然为null
     * 此注解也可以用在一些简单接口，不必校验token,加快请求响应速度
     * <p>
     * 此接口 没有 {@code AopLog}注解，因此完全不会收集日志
     *
     * @return result
     */
    @PostMapping("/articles1")
    @AllowVisitor(checkToken = false)
    public Result articles1() {

        return Result.success();
    }

    /**
     * 此方法添加了 {@code AopLog}注解，
     * 并且{@code saveLog}为{@code false}，只会记录发生异常时的记录
     *
     * @return result
     */
    @PostMapping("/users")
    @AopLog(saveLog = false, name = "文章列表2", platform = "用户端")
    public Result users() {
        List<User> list = userService.list();
        return Result.success(list);
    }


    @PostMapping("/saveArticle")
    @AopLog(name = "保存文章", platform = "用户端")
    public Result saveArticle(@RequestBody ArticleVO param) {
        Article article = BeanKit.copy(param, Article.class);
        boolean save = articleService.save(article);
        return Result.condition(save, "保存失败");
    }
}
