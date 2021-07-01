package com.leiran.security.provider;

import com.leiran.security.token.UserUsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;


public class UserAuthenticationProvider extends DaoAuthenticationProvider {

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UserUsernamePasswordAuthenticationToken.class);
    }
}
