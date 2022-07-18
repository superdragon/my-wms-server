package com.jiaming.wms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author dragon
 */
@EnableAsync  // 启用异步任务功能
@EnableScheduling // 启用定时任务功能
@SpringBootApplication
public class MyWmsApp {
    public static void main(String[] args) {
        SpringApplication.run(MyWmsApp.class, args);
    }
}
