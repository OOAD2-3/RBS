package com.rbs.project;

import com.rbs.project.socket.WebSocket;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@MapperScan(basePackages = "com.rbs.project.mapper")
@EnableTransactionManagement
@EnableAutoConfiguration
public class ProjectApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(ProjectApplication.class, args);
        WebSocket.setApplicationContext(applicationContext);
    }
}

