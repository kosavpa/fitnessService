package com.owl.fitness_service.config.security;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.security.web.server.savedrequest.NoOpServerRequestCache;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.server.WebFilter;


@SuppressWarnings("unused")
@Configuration
public class SecurityConfig {
    private final String allowOrigin;

    public SecurityConfig(@Value("${allowOrigin}") String allowOrigin) {
        this.allowOrigin = allowOrigin;
    }

    @Bean("webFilterChain")
    public SecurityWebFilterChain getMainConfig(ServerHttpSecurity httpSecurity,
                                                WebFilter jwtFilter) {
        return httpSecurity
                .addFilterAt(jwtFilter, SecurityWebFiltersOrder.HTTP_BASIC)
                .authorizeExchange(auth -> auth
                        .pathMatchers(HttpMethod.GET, "/articles")
                        .permitAll()
                        .pathMatchers(HttpMethod.GET, "/img/**")
                        .permitAll()
                        .pathMatchers(HttpMethod.GET, "/article")
                        .authenticated())
                .cors(this::configureCors)
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .requestCache(requestCacheSpec -> requestCacheSpec.requestCache(NoOpServerRequestCache.getInstance()))
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .build();
    }

    public void configureCors(ServerHttpSecurity.CorsSpec corsSpec) {
        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.addAllowedOrigin(allowOrigin);
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addExposedHeader(HttpHeaders.LOCATION);

        corsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        corsSpec.configurationSource(corsConfigurationSource);
    }
}