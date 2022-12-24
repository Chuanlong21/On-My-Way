package com.chuan.on_my_way;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@Slf4j
@ServletComponentScan
@EnableTransactionManagement
public class OnMyWayApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnMyWayApplication.class, args);
    }

}
