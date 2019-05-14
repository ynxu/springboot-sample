package com.light4j.sample.aspect;

import com.light4j.sample.BaseTests;
import org.junit.Test;
import org.light4j.sample.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;

public class AspectTests extends BaseTests {

    @Autowired
    ConfigService configService;

    @Test
    public void testAspect() {
        String article = configService.getArticle();
        System.out.println(article);
    }
}
