package com.scut.mplas.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TestController {

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    String Test(){
        return "hello world";
    }
}
