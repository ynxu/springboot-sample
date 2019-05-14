package org.light4j.sample.controller;

import org.light4j.sample.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/sample")
public class HelloController {

    @Autowired
    ConfigService configService;

    @GetMapping(value = "/hello")
    public String hello(){
        return "hello";
    }
    @GetMapping(value = "/article")
    public String article() {
        return configService.getArticle();
    }

}
