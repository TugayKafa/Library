package com.endava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AssignmentApi {

    public static void main(String[] args) {
        SpringApplication.run(AssignmentApi.class, args);
    }

}
