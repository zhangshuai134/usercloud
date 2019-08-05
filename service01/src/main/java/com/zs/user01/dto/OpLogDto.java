package com.zs.user01.dto;

import lombok.Data;

import java.util.Date;

@Data
public class OpLogDto {

    //用户ip
    private String ip;

    //用户名
    private String userName;

    //方法名
    private String methodName;

    //类全名
    private String className;

    //入参名字
    private String paramNames;

    //入参值
    private String paramValues;

    //注解描述
    private String description;

    //当前时间
    private Date opTime = new Date();
}
