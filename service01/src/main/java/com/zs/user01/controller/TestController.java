package com.zs.user01.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

    @RequestMapping("/{id}")
    public String test(@PathVariable("id") String id) {
        return "success+" + id;
    }

    @GetMapping("/get")
    public String testGet(String id){
        return "testGet"+id;
    }

    @PostMapping("/post")
    public String testPost(String id){
        return "testPost"+id;
    }
}
