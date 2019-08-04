package com.zs.user01.entity;

import lombok.Data;

@Data
public class RoleMenu {

    private Long menuId;
    private String menuName;
    private String menuUrl;
    private Long roleId;
    private String roleEnName;
}
