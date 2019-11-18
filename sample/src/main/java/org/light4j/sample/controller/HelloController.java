package org.light4j.sample.controller;

import org.light4j.sample.bean.Article;
import org.light4j.sample.bean.Response;
import org.light4j.sample.config.ArticleConfig;
import org.light4j.sample.exception.RestCode;
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
    public Response<String> hello() {
        return new Response(RestCode.SUCCESS, "hello");
    }

    @GetMapping(value = "/article")
    public Response<ArticleConfig> article() {
        return new Response(RestCode.SUCCESS, configService.getArticle());
    }

}
