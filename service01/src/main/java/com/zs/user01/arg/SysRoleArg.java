package com.zs.user01.arg;

import lombok.Data;

import java.util.List;

@Data
public class SysRoleArg {

    private Long id;

    private String name;

    private String enname;

    private List<Long> resourceIds;

}