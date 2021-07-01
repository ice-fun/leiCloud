package com.leiran.security.provider;

import com.leiran.security.token.AdminUsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;


public class AdminAuthenticationProvider extends DaoAuthenticationProvider {

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(AdminUsernamePasswordAuthenticationToken.class);
    }
}
