package com.leiran.security.token;

import com.leiran.security.AuthUserDetails;
import com.leiran.security.param.MiniLoginParam;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


public class UserMiniProgramAuthenticationToken extends MiniProgramAuthenticationToken {
    public UserMiniProgramAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
    }

    public UserMiniProgramAuthenticationToken(String code, String encryptedData, String iv) {
        super(code, encryptedData, iv);
    }

    public UserMiniProgramAuthenticationToken(MiniLoginParam miniLoginParam) {
        super(miniLoginParam);
    }

    public UserMiniProgramAuthenticationToken(Collection<? extends GrantedAuthority> authorities, AuthUserDetails userDetails, String code, String encryptedData, String iv) {
        super(authorities, userDetails, code, encryptedData, iv);
    }

    public UserMiniProgramAuthenticationToken(Collection<? extends GrantedAuthority> authorities, AuthUserDetails userDetails, MiniLoginParam miniLoginParam) {
        super(authorities, userDetails, miniLoginParam);
    }
}
