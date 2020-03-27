package com.swb.security.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author swb
 * 时间  2020-03-26 16:53
 * 文件  TestController2
 */
@RestController
public class TestController {
    @GetMapping("/user2")
    public String getHello() {
        return "hello";
    }
}
