package com.owl.fitness_service;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;


@SpringBootApplication
@Configuration(proxyBeanMethods = false)
public class AuthenticatedServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthenticatedServiceApplication.class, args);
    }
}