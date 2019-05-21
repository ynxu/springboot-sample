package com.light4j.sample.config;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.light4j.sample.SampleApplication;
import org.light4j.sample.bean.Article;
import org.light4j.sample.config.ArticleConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = SampleApplication.class)
public class ConfigTests {

    @Autowired
    ArticleConfig articleConfig;

    @Autowired
    Article article;


    @Test
    public void testArticleConfig() {
        System.out.println(articleConfig);
    }

    @Test
    public void testArticle() {
        System.out.println(article);
    }
}
