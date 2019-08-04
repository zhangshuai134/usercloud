package com.zs.user01.vo;

import lombok.Data;

@Data
public class SysUserVo {

    private Long id;

    private String loginName;

    private String password;

    private String name;

    private String lang;

    private String email;

    private String phone;

    private Long roleId;

    private String enname;//角色英文名
}