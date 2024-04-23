package com.owl.fitness_service.router;


import com.owl.fitness_service.entity.User;
import com.owl.fitness_service.service.user.UserService;
import com.owl.fitness_service.service.user.UsernameIsExistException;
import jakarta.annotation.Nonnull;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.stream.Collectors;


@Component("signUpRouter")
public class SignUpRouter implements RouterFunction<ServerResponse> {
    private final UserService service;

    @Autowired
    public SignUpRouter(UserService service) {
        this.service = service;
    }

    @Nonnull
    @Override
    public Mono<HandlerFunction<ServerResponse>> route(@Nonnull ServerRequest request) {
        if (RequestPredicates.POST("/signup").test(request)) {
            return Mono.just(req -> req.bodyToMono(User.class)
                    .doOnNext(service::saveUser)
                    .map(user -> getCreated())
                    .onErrorResume(this::getBadRequest));
        } else {
            return Mono.empty();
        }
    }

    private ServerResponse getCreated() {
        return ServerResponse.created(URI.create("/signin"))
                .contentType(MediaType.TEXT_PLAIN)
                .build()
                .block();
    }

    private Mono<ServerResponse> getBadRequest(Throwable throwable) {
        return ServerResponse.badRequest()
                .contentType(MediaType.TEXT_PLAIN)
                .body(Mono.just(getExceptionDescription(throwable)), String.class);
    }

    private String getExceptionDescription(Throwable throwable) {
        if (throwable instanceof ConstraintViolationException violationException) {
            return violationException.getConstraintViolations().stream()
                    .map(constraint -> constraint.getPropertyPath() + ":" + constraint.getMessage())
                    .collect(Collectors.joining("|"));
        } else if (throwable instanceof UsernameIsExistException violationException) {
            return violationException.getMessage();
        } else {
            return "";
        }
    }
}