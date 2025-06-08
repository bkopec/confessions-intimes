package com.bkopec.confintimes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing; // Make sure this import is here

@SpringBootApplication
@EnableJpaAuditing // Keep this to enable @CreatedDate and @LastModifiedDate
public class ConfessionsIntimesApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfessionsIntimesApplication.class, args);
    }
}