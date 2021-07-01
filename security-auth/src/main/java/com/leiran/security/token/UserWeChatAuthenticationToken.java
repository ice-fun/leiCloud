package com.leiran.security.token;

import com.leiran.security.AuthUserDetails;
import com.leiran.security.param.WxLoginParam;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


public class UserWeChatAuthenticationToken extends WeChatAuthenticationToken {
    public UserWeChatAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
    }

    public UserWeChatAuthenticationToken(String code) {
        super(code);
    }

    public UserWeChatAuthenticationToken(WxLoginParam wxLoginParam) {
        super(wxLoginParam);
    }

    public UserWeChatAuthenticationToken(Collection<? extends GrantedAuthority> authorities, AuthUserDetails userDetails, String code) {
        super(authorities, userDetails, code);
    }

    public UserWeChatAuthenticationToken(Collection<? extends GrantedAuthority> authorities, AuthUserDetails userDetails, WxLoginParam wxLoginParam) {
        super(authorities, userDetails, wxLoginParam);
    }
}
