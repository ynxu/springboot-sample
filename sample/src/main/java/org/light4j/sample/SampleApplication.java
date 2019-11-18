package org.light4j.sample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@Slf4j
@SpringBootApplication
@ImportResource(value = {"classpath:bean/application-bean.xml"})
public class SampleApplication {


    public static void main(String[] args) {
        SpringApplication.run(SampleApplication.class, args);

//        /**
//         * 关闭Banner方法一
//         */
        // SpringApplication application = new
        // SpringApplication(HelloApplication.class);
        // application.setBannerMode(Banner.Mode.OFF);
        // application.run(args);
//        /**
//         * 关闭Banner方法二
//         */
//        new SpringApplicationBuilder(Application.class).bannerMode(Banner.Mode.OFF).run(args);
    }
}
