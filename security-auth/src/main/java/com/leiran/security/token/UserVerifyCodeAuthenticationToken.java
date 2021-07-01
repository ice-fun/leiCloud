package com.leiran.security.token;

import com.leiran.security.AuthUserDetails;
import com.leiran.security.param.VerifyParam;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


public class UserVerifyCodeAuthenticationToken extends VerifyCodeAuthenticationToken {
    public UserVerifyCodeAuthenticationToken(String phoneNumber, String code) {
        super(phoneNumber, code);
    }

    public UserVerifyCodeAuthenticationToken(VerifyParam verifyParam) {
        super(verifyParam);
    }

    public UserVerifyCodeAuthenticationToken(Collection<? extends GrantedAuthority> authorities, AuthUserDetails principal, String phoneNumber, String code, Boolean authenticated) {
        super(authorities, principal, phoneNumber, code, authenticated);
    }

    public UserVerifyCodeAuthenticationToken(Collection<? extends GrantedAuthority> authorities, AuthUserDetails principal, VerifyParam verifyParam, Boolean authenticated) {
        super(authorities, principal, verifyParam, authenticated);
    }

    public UserVerifyCodeAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
    }
}
