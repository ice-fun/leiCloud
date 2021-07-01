package com.leiran.security.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserVO {

    private String userId;
    private String nickname;
    private String userAccount;
    private String userPhone;
    private Boolean subscribed;
    private Boolean locked;

    private String token;


}