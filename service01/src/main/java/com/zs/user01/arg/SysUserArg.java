package com.zs.user01.arg;

import lombok.Data;

@Data
public class SysUserArg {

    private Long id;

    private String loginName;

    private String password;

    private String name;

    private String email;

    private String phone;

    private Long roleId;

}