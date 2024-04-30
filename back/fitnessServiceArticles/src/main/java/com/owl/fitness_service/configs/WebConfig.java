package com.owl.fitness_service.configs;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.security.web.server.savedrequest.NoOpServerRequestCache;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;


@SuppressWarnings("unused")
@Configuration
public class WebConfig {
    @Value("${allowOrigin}")
    private String allowOrigin;


    @Bean("webFilterChain")
    public SecurityWebFilterChain getMainConfig(ServerHttpSecurity httpSecurity,
                                                ReactiveAuthenticationManager authManager) {
        return httpSecurity.authorizeExchange(auth -> auth
                        .pathMatchers(HttpMethod.GET, "/article")
                        .authenticated()
                        .pathMatchers(HttpMethod.GET, "/img/**")
                        .authenticated()
                        .pathMatchers(HttpMethod.GET, "/articles")
                        .permitAll())
                .cors(this::configureCors)
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(httpBasicCustomizer -> httpBasicCustomizer.authenticationManager(authManager))
                .requestCache(requestCacheSpec -> requestCacheSpec.requestCache(NoOpServerRequestCache.getInstance()))
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .build();
    }

    @Bean("corsConfig")
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