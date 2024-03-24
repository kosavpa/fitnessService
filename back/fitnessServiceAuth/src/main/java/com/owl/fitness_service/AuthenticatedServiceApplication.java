package com.owl.fitness_service;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@SpringBootApplication
@Configuration(proxyBeanMethods = false)
public class AuthenticatedServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthenticatedServiceApplication.class, args);
    }

    @Bean
    public RouterFunction<ServerResponse> userAndAdminInfo() {
        return RouterFunctions.route(
                RequestPredicates.GET("/user"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .body(Mono.just("THIS IS USER AND ADMIN INFORMATION"), String.class));
    }

    @Bean
    public RouterFunction<ServerResponse> adminInfo() {
        return RouterFunctions.route(
                RequestPredicates.GET("/admin"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .body(Mono.just("THIS IS ADMIN INFORMATION"), String.class));
    }

    @Bean
    public RouterFunction<ServerResponse> publicInfo() {
        return RouterFunctions.route(
                RequestPredicates.GET("/"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .body(Mono.just("THIS IS PUBLIC INFORMATION"), String.class));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ApplicationRunner data(UserRepository repository, PasswordEncoder passwordEncoder) {
        return arg -> {
            User simpleUser = new User();

            simpleUser.setRole(Role.USER);
            simpleUser.setUsername("user");
            simpleUser.setPassword(passwordEncoder.encode("user"));

            repository.save(simpleUser);

            User admin = new User();

            admin.setRole(Role.ADMIN);
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));

            repository.save(admin);
        };
    }
}