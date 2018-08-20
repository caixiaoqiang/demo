package com.cookie.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cxq
 * @date 2018/8/17 15:50
 */
@RestController
@RequestMapping("test")
public class TestController {

    @RequestMapping("/hello")
    public String test() {
        return "Hello World";
    }
}
