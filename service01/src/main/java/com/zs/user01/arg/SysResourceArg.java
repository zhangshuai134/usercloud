package com.zs.user01.arg;

import lombok.Data;

@Data
public class SysResourceArg {

    private Long id;

    private String name;

    private Integer sort;

    private String href;

    private Long parentId;

    private Integer type;

    private String remark;

}