package com.zs.user01.vo;

import lombok.Data;

@Data
public class SysResourceVo {

    private Long id;

    private String name;

    private Integer sort;

    private String href;

    private Long parentId;

    private Integer type;

    private String remark;

    private Boolean hasChildren = false;

    private Boolean isLeaf = true;

}