package com.leiran.security.token;

import com.leiran.security.AuthUserDetails;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


public class UserJwtAuthenticationToken extends JwtAuthenticationToken {
    public UserJwtAuthenticationToken(String token) {
        super(token);
    }

    public UserJwtAuthenticationToken(boolean visitor) {
        super(visitor);
    }

    public UserJwtAuthenticationToken(AuthUserDetails principal, String token, Collection<? extends GrantedAuthority> authorities) {
        super(principal, token, authorities);
    }
}
