package com.example.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppApplication {
    public static void main(String[] args) {
        // This single line turns your Java code into a live web server!
        SpringApplication.run(AppApplication.class, args);
    }
}
