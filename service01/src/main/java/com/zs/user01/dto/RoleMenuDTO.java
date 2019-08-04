package com.zs.user01.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RoleMenuDTO implements Serializable {
    private long menuId;
    private String menu;
    private List<String> button;
    private long parentId;
}
