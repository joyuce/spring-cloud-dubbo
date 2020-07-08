package com.j.openproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class D1ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(D1ServerApplication.class, args);
    }

}
