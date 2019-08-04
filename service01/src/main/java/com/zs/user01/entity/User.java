package com.zs.user01.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@TableName("user")
@Data
public class User {
    @TableId(type=IdType.AUTO)
    private String id;
    @TableField("name")
    private String name;
    private String email;
    private String phone;
}
