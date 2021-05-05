package com.feng.community;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author fengyunan
 * Created on 2021-03-04
 */
@SpringBootApplication
@EnableScheduling
@MapperScan(basePackages = "com.feng.community.dao")
public class CommunityApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommunityApplication.class, args);
    }
}
