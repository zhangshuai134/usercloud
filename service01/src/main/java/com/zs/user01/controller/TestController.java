package com.zs.user01.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping("/{id}")
    public String test(@PathVariable("id") String id) {
        return "success+" + id;
    }
}
