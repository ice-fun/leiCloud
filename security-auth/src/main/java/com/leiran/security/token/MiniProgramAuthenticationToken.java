package com.leiran.security.token;

import com.leiran.security.AuthUserDetails;
import com.leiran.security.param.MiniLoginParam;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class MiniProgramAuthenticationToken extends AbstractAuthenticationToken {
    @Getter
    private String code;
    @Getter
    private String encryptedData;
    @Getter
    private String iv;

    @Getter
    private MiniLoginParam miniLoginParam;

    private AuthUserDetails userDetails;

    /**
     * Creates a token with the supplied array of authorities.
     *
     * @param authorities the collection of <tt>GrantedAuthority</tt>s for the principal
     *                    represented by this authentication object.
     */
    public MiniProgramAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
    }

    public MiniProgramAuthenticationToken(String code) {
        super(Collections.emptyList());
        this.code = code;
    }

    public MiniProgramAuthenticationToken(String code, String encryptedData, String iv) {
        super(null);
        this.code = code;
        this.encryptedData = encryptedData;
        this.iv = iv;
    }

    public MiniProgramAuthenticationToken(MiniLoginParam miniLoginParam) {
        super(null);
        this.miniLoginParam = miniLoginParam;
    }

//    public MiniProgramAuthenticationToken(Collection<? extends GrantedAuthority> authorities, AuthUserDetails userDetails, String code) {
//        super(authorities);
//        this.userDetails = userDetails;
//        this.code = code;
//    }

    public MiniProgramAuthenticationToken(Collection<? extends GrantedAuthority> authorities, AuthUserDetails userDetails, String code, String encryptedData, String iv) {
        super(authorities);
        this.userDetails = userDetails;
        this.code = code;
        this.encryptedData = encryptedData;
        this.iv = iv;
    }

    public MiniProgramAuthenticationToken(Collection<? extends GrantedAuthority> authorities, AuthUserDetails userDetails, MiniLoginParam miniLoginParam) {
        super(authorities);
        this.userDetails = userDetails;
        this.miniLoginParam = miniLoginParam;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return userDetails;
    }
}
