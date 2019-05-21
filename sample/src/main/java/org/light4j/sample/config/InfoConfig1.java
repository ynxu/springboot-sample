package org.light4j.sample.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@PropertySource(value = {"classpath:config/info.properties"})
@ConfigurationProperties(prefix = "info")
public class InfoConfig1 {

    private String address;
    private String company;
    private String degree;

}
