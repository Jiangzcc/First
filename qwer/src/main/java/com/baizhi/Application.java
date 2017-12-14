package com.baizhi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

//@SpringBootApplication = (默认属性)@Configuration + @EnableAutoConfiguration + @ComponentScan
@SpringBootApplication
//扫描DAO
@MapperScan("com.baizhi.dao")
//开启定时任务
@EnableScheduling
//开启缓存
@EnableCaching
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}