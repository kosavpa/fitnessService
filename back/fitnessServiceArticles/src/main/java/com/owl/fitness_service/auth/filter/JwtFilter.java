package com.owl.fitness_service.auth.filter;


import com.owl.fitness_service.auth.jwtservice.JwtTokenService;
import com.owl.fitness_service.auth.userservice.AuthService;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Optional;


@Component("jwtFilter")
public class JwtFilter implements WebFilter {
    private final JwtTokenService jwtService;

    private final AuthService authService;

    @Autowired
    public JwtFilter(JwtTokenService jwtService, AuthService authService) {
        this.jwtService = jwtService;
        this.authService = authService;
    }

    @Nonnull
    @Override
    public Mono<Void> filter(@Nonnull ServerWebExchange exchange, @Nonnull WebFilterChain chain) {
        extractJwtCookie(exchange.getRequest())
                .ifPresent(tokenCookie -> {
                    String token = tokenCookie.getValue();

                    jwtService.checkTokenExpired(token);

                    ReactiveSecurityContextHolder.withAuthentication(authService.createAuthByJwt(token));
                });

        return chain.filter(exchange);
    }

    private Optional<HttpCookie> extractJwtCookie(ServerHttpRequest request) {
        return Optional.ofNullable(request.getCookies().get("authToken"))
                .filter(httpCookies -> !httpCookies.isEmpty())
                .map(httpCookies -> httpCookies.get(0));
    }
}