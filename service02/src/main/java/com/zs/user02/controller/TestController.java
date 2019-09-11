package com.zs.user02.controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: curry.zhang
 * @Date: 2019/8/21 19:09
 * @Description:
 */
@RestController
public class TestController {

    @GetMapping("/get")
    public String testGet(String id) {
        return "testGet" + id;
    }

    @PostMapping("/post")
    public String testPost(Aaa aa) {
        System.out.println("testPost,id=" + aa.getId() + "," + aa.getName());
        return "testPost" + aa.getId();
    }

    @PostMapping("/post1")
    public String testPost1(@RequestParam("id") String id) {
        System.out.println("testPost1,id=" + id);
        return "testPost1" + id;
    }
    @PostMapping("/post2")
    public Aaa testPost2(@RequestParam("LS") List<String> LS) {
        for (String l : LS) {
            System.out.println(l);
        }
        Aaa aaa = new Aaa();
        aaa.setId("asd");
        aaa.setName("qwe");
        return aaa;
    }

}
