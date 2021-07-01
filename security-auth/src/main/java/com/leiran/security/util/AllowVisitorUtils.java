package com.leiran.security.util;

import lombok.Data;
import lombok.Getter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Date 2021/4/22
 **/


public class AllowVisitorUtils {
    @Getter
    private final List<VisitorRequest> matchers;

    public AllowVisitorUtils() {
        this.matchers = AnnotationUtil.getAllowVisitorUrl();
    }

    @Data
    public static class VisitorRequest {
        private RequestMatcher path;
        private boolean authentication;
    }

    public boolean anyPermission(HttpServletRequest request) {
        return this.matchers.stream().filter(matcher -> !matcher.isAuthentication()).anyMatch(matcher -> matcher.path.matches(request));
    }

    public boolean visitorPermission(HttpServletRequest request) {
        return this.matchers.stream().filter(VisitorRequest::isAuthentication).anyMatch(matcher -> matcher.path.matches(request));
    }
}
