package com.leiran.security.provider;

import com.leiran.common.enums.RoleEnum;
import com.leiran.common.utils.JwtTokenUtils;
import com.leiran.security.AuthUserDetails;
import com.leiran.security.AuthUserDetailsService;
import com.leiran.security.account.User;
import com.leiran.security.token.JwtAuthenticationToken;
import io.jsonwebtoken.JwtException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.StringUtils;


public class JwtAuthenticationProvider extends AbstractMatchClassAuthenticationProvider {
    private final String tokenKey;

    public JwtAuthenticationProvider(AuthUserDetailsService userDetailsService, Class<? extends JwtAuthenticationToken> matchClass, String tokenKey) {
        super(userDetailsService, matchClass);
        this.userDetailsService = userDetailsService;
        this.tokenKey = tokenKey;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        String token = jwtAuthenticationToken.getToken();
        boolean visitor = jwtAuthenticationToken.isVisitor();
        // 访客访问时候，创建新对象，避免空指针
        if (visitor) {
            User user = new User();
            user.setRole(RoleEnum.ROLE_VISITOR.name());
            return new JwtAuthenticationToken(user, token, user.getAuthorities());
        }
        if (!StringUtils.hasText(token)) {
            throw new InsufficientAuthenticationException("尚未登录");
        }
        AuthUserDetails authUserDetails;
        try {
            String username = JwtTokenUtils.getUsernameFromToken(token);
            authUserDetails = userDetailsService.loadUserById(username);
            checkAuthUserDetails(authUserDetails);
            JwtTokenUtils.validateToken(token, authUserDetails.getId(), authUserDetails.getTokenVersionMap().get(tokenKey));
        } catch (JwtException e) {
            throw new BadCredentialsException(e.getMessage(), null);
        }
        return new JwtAuthenticationToken(authUserDetails, token, authUserDetails.getAuthorities());
    }
}
