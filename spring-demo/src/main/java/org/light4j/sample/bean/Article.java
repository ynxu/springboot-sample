package org.light4j.sample.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Article {
    @Value("${article.name}")
    private String name;
    @Value("${article.author}")
    private String author;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Article{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
