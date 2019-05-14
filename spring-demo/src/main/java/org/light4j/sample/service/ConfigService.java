package org.light4j.sample.service;

import org.light4j.sample.annotation.LogAction;
import org.light4j.sample.config.ArticleConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigService {

    @Autowired
    ArticleConfig articleConfig;

    @LogAction(value = "getArticle")
    public String getArticle() {
        return articleConfig.toString();
    }


}
