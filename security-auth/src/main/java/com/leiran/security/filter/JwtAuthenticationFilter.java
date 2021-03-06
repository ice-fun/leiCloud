package com.leiran.security.filter;

import com.leiran.security.token.JwtAuthenticationToken;
import com.leiran.security.token.UserJwtAuthenticationToken;
import com.leiran.security.util.AllowVisitorUtils;
import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Jwt 拦截器
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Resource
    private AllowVisitorUtils allowVisitorUtils;

    Class<? extends JwtAuthenticationToken> auth;
    private final AntPathRequestMatcher requiresAuthenticationRequestMatcher;
    private List<RequestMatcher> permissiveRequestMatchers;
    @Setter
    private AuthenticationManager authenticationManager;
    @Setter
    private AuthenticationSuccessHandler successHandler;
    @Setter
    private AuthenticationFailureHandler failureHandler;


    public JwtAuthenticationFilter(String path, Class<? extends JwtAuthenticationToken> auth) {
        this.requiresAuthenticationRequestMatcher = new AntPathRequestMatcher(path);
        this.auth = auth;
    }

    public JwtAuthenticationFilter(String path, Class<? extends JwtAuthenticationToken> auth, AuthenticationSuccessHandler successHandler, AuthenticationFailureHandler failureHandler, String... urls) {
        //拦截header中带token的请求
        this.requiresAuthenticationRequestMatcher = new AntPathRequestMatcher(path);
        this.auth = auth;
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
        this.setPermissiveUrl(urls);
    }

    public static JwtAuthenticationFilter _new(String path, Class<? extends JwtAuthenticationToken> auth, AuthenticationSuccessHandler successHandler, AuthenticationFailureHandler failureHandler, String... urls) {
        return new JwtAuthenticationFilter(path, auth, successHandler, failureHandler, urls);
    }

    /**
     * 获取 headers里的参数token
     * 适用于 一般http请求
     *
     * @param request HttpServletRequest
     * @return token
     */
    private String getJwtToken(HttpServletRequest request) {
        String authInfo = request.getHeader("token");
        return StringUtils.removeStart(authInfo, "Bearer ");
    }

    /**
     * 获取 headers里的参数token
     * 适用于 websocket
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @return token
     */
    private String getJwtToken(HttpServletRequest request, HttpServletResponse response) {
        String authInfo = request.getHeader("Sec-WebSocket-Protocol");
        response.setHeader("Sec-WebSocket-Protocol", authInfo);
        return StringUtils.removeStart(authInfo, "Bearer ");
    }

    @SneakyThrows
    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, FilterChain filterChain) {
        if (permissiveRequest(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        if (!requiresAuthenticationRequestMatcher.matches(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = getJwtToken(request);

        Authentication authResult = null;
        AuthenticationException failed = null;

        /*
            如果接口设置为允许游客访问，并且不校验token,那么此接口请求直接通过
         */
        if (allowVisitorUtils.anyPermission(request) || (StringUtils.isBlank(token) && allowVisitorUtils.visitorPermission(request))) {
            authResult = new UserJwtAuthenticationToken(true);
            successfulAuthentication(request, response, filterChain, authResult);
        } else {

            try {
                JwtAuthenticationToken jwtAuthenticationToken = auth.getConstructor(String.class).newInstance(token);
                authResult = authenticationManager.authenticate(jwtAuthenticationToken);
            } catch (AuthenticationException e) {
                failed = e;
            }
            if (authResult != null) {
                successfulAuthentication(request, response, filterChain, authResult);
            } else {
                unsuccessfulAuthentication(request, response, failed);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void successfulAuthentication(HttpServletRequest request,
                                          HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        if (successHandler != null) {
            successHandler.onAuthenticationSuccess(request, response, chain, authResult);
        }
    }

    private void unsuccessfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        failureHandler.onAuthenticationFailure(request, response, failed);
    }

    private boolean permissiveRequest(HttpServletRequest request) {
        if (permissiveRequestMatchers == null) {
            return false;
        }
        for (RequestMatcher permissiveMatcher : permissiveRequestMatchers) {
            if (permissiveMatcher.matches(request)) {
                return true;
            }
        }
        return false;
    }

    public void setPermissiveUrl(String... urls) {
        if (permissiveRequestMatchers == null) {
            permissiveRequestMatchers = new ArrayList<>();
        }
        for (String url : urls) {
            permissiveRequestMatchers.add(new AntPathRequestMatcher(url));
        }
    }
}
