package com.zs.user01.controller;

import com.alibaba.fastjson.JSON;
import com.zs.user01.entity.User;
import com.zs.user01.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping("/getUserById")
    public String getUserById(String id){
        User user = userService.getUserById(id);
        System.out.println("user="+ JSON.toJSONString(user));
        return "zhang san";
    }
}
