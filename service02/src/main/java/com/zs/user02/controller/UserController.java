package com.zs.user02.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/getUserById")
    public String getUserById(String id){
        return restTemplate.getForObject("http://localhost:8001/user/getUserById?id="+id,String.class);
    }
}
