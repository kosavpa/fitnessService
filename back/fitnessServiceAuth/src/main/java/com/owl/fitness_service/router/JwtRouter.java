package com.owl.fitness_service.router;


import com.owl.fitness_service.service.jwt.JwtService;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;


@Component("jwtRouter")
public class JwtRouter implements RouterFunction<ServerResponse> {
    private final JwtService jwtService;

    @Autowired
    public JwtRouter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Nonnull
    @Override
    public Mono<HandlerFunction<ServerResponse>> route(@Nonnull ServerRequest request) {
        if (RequestPredicates.GET("/signin").test(request)) {
            return Mono.just(req -> ServerResponse.ok()
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(generateTokenForBody(), String.class));
        } else {
            return Mono.empty();
        }
    }

    private Mono<String> generateTokenForBody() {
        return ReactiveSecurityContextHolder.getContext().map(context ->
                jwtService.generateToken(
                        (UserDetails) context.getAuthentication().getPrincipal()));
    }
}