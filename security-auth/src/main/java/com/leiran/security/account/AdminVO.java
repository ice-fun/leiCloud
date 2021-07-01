package com.leiran.security.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdminVO {

    private String adminId;
    private String nickname;
    private String adminPhone;
    private String adminAccount;
    private Boolean subscribed;
    private String role;

    private String token;
}
