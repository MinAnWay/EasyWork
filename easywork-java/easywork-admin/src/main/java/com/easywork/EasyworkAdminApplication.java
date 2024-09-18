package com.easywork;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.easywork"})
@EnableTransactionManagement
@EnableScheduling
@EnableAsync
@MapperScan(basePackages = {"com.easywork.mappers"})
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 2400, redisNamespace = "easywork:session")
public class EasyworkAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(EasyworkAdminApplication.class, args);
    }
}
