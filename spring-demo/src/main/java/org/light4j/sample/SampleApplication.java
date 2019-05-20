package org.light4j.sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;


@SpringBootApplication
@ImportResource(value = {"classpath:bean/application-bean.xml"})
public class SampleApplication {
    private static Logger logger = LoggerFactory.getLogger(SampleApplication.class);

    public static void main(String[] args) {
        logger.debug("main");
        for (String arg : args) {
            logger.debug(arg);
        }
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
