package com.leiran.security.provider;

import com.leiran.security.AuthUserDetails;
import com.leiran.security.AuthUserDetailsService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;

public abstract class AbstractMatchClassAuthenticationProvider implements AuthenticationProvider {
    @Getter
    @Setter
    protected AuthUserDetailsService userDetailsService;
    protected Class<? extends Authentication> matchClass;

    public AbstractMatchClassAuthenticationProvider(AuthUserDetailsService userDetailsService, Class<? extends Authentication> matchClass) {
        this.userDetailsService = userDetailsService;
        this.matchClass = matchClass;
    }

    public void checkAuthUserDetails(AuthUserDetails authUserDetails) {
        if (authUserDetails == null) {
            throw new BadCredentialsException("账号不存在");
        }
        if (!authUserDetails.isAccountNonLocked()) {
            throw new BadCredentialsException("账号已被冻结");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(matchClass);
    }
}
