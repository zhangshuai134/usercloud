package com.zs.user01.controller;

import com.zs.user01.entity.User;
import com.zs.user01.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @RequestMapping("/getUserById")
    public String getUserById(String id){
        User user = userService.getUserById(id);
        return "zhang san";
    }
}
