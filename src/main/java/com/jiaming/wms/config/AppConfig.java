package com.jiaming.wms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author dragon
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppConfig {
    private String savePath;
    private String httpPath;
    private List<String> imageTypes;
    private List<String> videoTypes;
    private Integer imageMaxSize;
    private Integer videoMaxSize;
}
