package com.zs.user01.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysUserDTO implements Serializable {

    private static final long serialVersionUID = -6576065081793648269L;
    private Long id;

    private String loginName;

    private String password;

    private String name;

    private String lang;

    private String email;

    private String roleName;

    private String roleEnName;

    private String phone;

    private String userId;

}