package com.easywork;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.easywork"})
@MapperScan(basePackages = {"com.easywork.mappers"})
@EnableTransactionManagement
@EnableScheduling
@EnableAsync
public class EasyworkApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(EasyworkApiApplication.class, args);
    }
}
