[toc]
# 1.读取application文件

application-dev.yml
```$xslt
article:
  name: spring inaction
  author: Craig Walls
```
## 1.1 @Value注解读取方式
Article.java
```$xslt
@Component
public class Article {
    @Value("${article.name}")
    private String name;
    @Value("${article.author}")
    private String author;
}
```

## 1.2 @ConfigurationProperties注解读取方式
```$xslt

@Component
@ConfigurationProperties(prefix = "article")
public class ArticleConfig {

    private String name;
    private String author;
    
}
```
# 2. 读取指定文件
config/info.properties
```$xslt
info.address=USA
info.company=Spring
info.degree=high
```
注意：@PropertySource不支持yml文件读取。

# 2.1 @PropertySource+@Value注解读取方式

```$xslt
@Data
@Component
@PropertySource(value = "config/info.properties")
public class InfoConfig {
    @Value("${info.address}")
    private String address;
    @Value("${info.company}")
    private String company;
    @Value("${info.degree}")
    private String degree;
}

```

## 2.2 @PropertySource+@ConfigurationProperties注解读取方式

```$xslt
@Data
@Component
@PropertySource(value = "config/info.properties")
@ConfigurationProperties(prefix = "info")
public class InfoConfig1 {

    private String address;
    private String company;
    private String degree;

}
```

# 3. Environment读取方式
以上所有加载出来的配置都可以通过Environment注入获取到。
```$xslt

@Autowired
private Environment env ;

// 获取参数
String getProperty (String key);
```