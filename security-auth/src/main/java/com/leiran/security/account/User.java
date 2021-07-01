package com.leiran.security.account;


import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.leiran.security.AuthUserDetails;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User implements AuthUserDetails {

    @TableId
    private String userId;
    private String nickname;
    private String userPhone;
    private String userAccount;
    @JsonIgnore
    private String userPassword;
    @JsonIgnore
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String miniOpenId;
    private Boolean subscribed;
    @JsonIgnore
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String officialOpenId;
    @JsonIgnore
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String wechatUnionId;
    @JsonIgnore
    private Integer tokenVersion;
    private String role;
    @JsonIgnore
    private Boolean locked;
    @JsonIgnore
    private Integer isDelete;
    @JsonIgnore
    private LocalDateTime createTime;
    @JsonIgnore
    private LocalDateTime updateTime;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return userPassword;
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return userAccount;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * 如果用户需要在不同的端同时登录并且互不影响，那么就应该 增加多个tokenVersion字段
     * 用以控制每个段的token版本号
     *
     * @return map
     */
    @JsonIgnore
    @Override
    public Map<String, Integer> getTokenVersionMap() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("tokenVersion", tokenVersion);
        return map;
    }

    @JsonIgnore
    @Override
    public String getId() {
        return userId;
    }

    @JsonIgnore
    @Override
    public String getName() {
        return nickname;
    }

    @JsonIgnore
    @Override
    public String getUnionId() {
        return wechatUnionId;
    }

    @JsonIgnore
    @Override
    public String getRole() {
        return role;
    }
}
