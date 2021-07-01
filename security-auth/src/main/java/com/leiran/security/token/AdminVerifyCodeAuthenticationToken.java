package com.leiran.security.token;

import com.leiran.security.AuthUserDetails;
import com.leiran.security.param.VerifyParam;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


public class AdminVerifyCodeAuthenticationToken extends VerifyCodeAuthenticationToken {
    public AdminVerifyCodeAuthenticationToken(String phoneNumber, String code) {
        super(phoneNumber, code);
    }

    public AdminVerifyCodeAuthenticationToken(VerifyParam verifyParam) {
        super(verifyParam);
    }

    public AdminVerifyCodeAuthenticationToken(Collection<? extends GrantedAuthority> authorities, AuthUserDetails principal, String phoneNumber, String code, Boolean authenticated) {
        super(authorities, principal, phoneNumber, code, authenticated);
    }

    public AdminVerifyCodeAuthenticationToken(Collection<? extends GrantedAuthority> authorities, AuthUserDetails principal, VerifyParam verifyParam, Boolean authenticated) {
        super(authorities, principal, verifyParam, authenticated);
    }

    public AdminVerifyCodeAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
    }
}
