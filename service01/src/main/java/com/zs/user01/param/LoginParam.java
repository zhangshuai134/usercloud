package com.zs.user01.param;

import com.zs.user01.annotation.NotNull;
import lombok.Data;

import java.io.Serializable;

@Data
public class LoginParam implements Serializable {
    @NotNull
    private String loginName;
    @NotNull
    private String pwd;
    private String captchaCode;
    private String token;
    private Boolean rememberMe;
    @NotNull
    private String lang;
}
