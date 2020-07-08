package com.j.openproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class D2ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(D2ServerApplication.class, args);
    }

}
