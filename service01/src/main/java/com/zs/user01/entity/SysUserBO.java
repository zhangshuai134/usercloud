package com.zs.user01.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserBO extends SysUser{
    @TableField("roleName")
    private String roleName;
    @TableField("roleEnName")
    private String roleEnName;
}
