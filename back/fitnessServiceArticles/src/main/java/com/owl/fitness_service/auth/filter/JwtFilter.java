package com.owl.fitness_service.auth.filter;


import com.owl.fitness_service.auth.jwtservice.JwtTokenService;
import com.owl.fitness_service.auth.userservice.AuthService;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

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
        return chain.filter(exchange)
                .contextWrite(context -> withSecurityContext(context, exchange));
    }

    private Context withSecurityContext(Context mainContext, ServerWebExchange exchange) {
        return mainContext.putAll(
                getMonoForContext(exchange)
                        .as(ReactiveSecurityContextHolder::withSecurityContext)
                        .readOnly());
    }

    private Mono<SecurityContextImpl> getMonoForContext(ServerWebExchange exchange) {
        return extractJwtCookie(exchange.getRequest())
                .map(this::checkFromCookieTokenAndGetAuth)
                .map(authentication -> Mono.just(new SecurityContextImpl(authentication)))
                .orElse(Mono.empty());
    }

    private Authentication checkFromCookieTokenAndGetAuth(HttpCookie tokenCookie) {
        jwtService.checkTokenExpired(tokenCookie.getValue());

        return authService.createAuthByJwt(tokenCookie.getValue());
    }

    private Optional<HttpCookie> extractJwtCookie(ServerHttpRequest request) {
        return Optional.ofNullable(request.getCookies().get("authToken"))
                .filter(httpCookies -> !httpCookies.isEmpty())
                .map(httpCookies -> httpCookies.get(0));
    }
}