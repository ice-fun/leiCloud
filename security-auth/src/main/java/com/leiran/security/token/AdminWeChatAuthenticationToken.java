package com.leiran.security.token;

import com.leiran.security.AuthUserDetails;
import com.leiran.security.param.WxLoginParam;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


public class AdminWeChatAuthenticationToken extends WeChatAuthenticationToken {
    public AdminWeChatAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
    }

    public AdminWeChatAuthenticationToken(String code) {
        super(code);
    }

    public AdminWeChatAuthenticationToken(WxLoginParam wxLoginParam) {
        super(wxLoginParam);
    }

    public AdminWeChatAuthenticationToken(Collection<? extends GrantedAuthority> authorities, AuthUserDetails userDetails, String code) {
        super(authorities, userDetails, code);
    }

    public AdminWeChatAuthenticationToken(Collection<? extends GrantedAuthority> authorities, AuthUserDetails userDetails, WxLoginParam wxLoginParam) {
        super(authorities, userDetails, wxLoginParam);
    }
}
