package com.zs.user01.entity;

import lombok.Data;

import java.util.List;

@Data
public class ResourceTree {

    private Long id;

    private String name;

    private List<ResourceTree> children;

    private Integer sort;

    private String href;

    private Long parentId;

    private Integer type;

    private String remark;



}
