package org.light4j.sample.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@PropertySource(value = {"classpath:config/info.properties"})
public class InfoConfig {

    @Value("${info.address}")
    private String address;
    @Value("${info.company}")
    private String company;
    @Value("${info.degree}")
    private String degree;

}
