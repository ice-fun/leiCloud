package com.leiran.security.token;

import com.leiran.security.AuthUserDetails;
import com.leiran.security.param.MiniLoginParam;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


public class AdminMiniProgramAuthenticationToken extends MiniProgramAuthenticationToken {
    public AdminMiniProgramAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
    }

    public AdminMiniProgramAuthenticationToken(String code, String encryptedData, String iv) {
        super(code, encryptedData, iv);
    }

    public AdminMiniProgramAuthenticationToken(MiniLoginParam miniLoginParam) {
        super(miniLoginParam);
    }

    public AdminMiniProgramAuthenticationToken(Collection<? extends GrantedAuthority> authorities, AuthUserDetails userDetails, String code, String encryptedData, String iv) {
        super(authorities, userDetails, code, encryptedData, iv);
    }

    public AdminMiniProgramAuthenticationToken(Collection<? extends GrantedAuthority> authorities, AuthUserDetails userDetails, MiniLoginParam miniLoginParam) {
        super(authorities, userDetails, miniLoginParam);
    }
}
