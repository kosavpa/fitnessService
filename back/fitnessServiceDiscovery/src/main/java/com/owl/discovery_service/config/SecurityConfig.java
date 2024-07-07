package com.owl.discovery_service.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.NullSecurityContextRepository;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


@SuppressWarnings("unused")
@Configuration
public class SecurityConfig {
    @Bean("webFilterChain")
    public SecurityFilterChain getMainConfig(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/actuator/**")
                        .hasRole("ACTUATOR_CLIENT")
                        .requestMatchers("/**")
                        .hasRole("CONFIG_CLIENT"))
                .cors(this::configureCors)
                .httpBasic(httpBasicSpec ->
                        httpBasicSpec.securityContextRepository(new NullSecurityContextRepository()))
                .csrf(AbstractHttpConfigurer::disable)
                .requestCache(requestCacheSpec -> requestCacheSpec.requestCache(new NullRequestCache()))
                .build();
    }

    private void configureCors(CorsConfigurer<HttpSecurity> corsSpec) {
        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowedOrigins(List.of("*"));
        corsConfiguration.setAllowedMethods(List.of("*"));
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addExposedHeader(HttpHeaders.LOCATION);

        corsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        corsSpec.configurationSource(corsConfigurationSource);
    }

    @Bean("simpleUserDetails")
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails configClient = User.builder()
                .username("configClient")
                .password("configClient")
                .roles("CONFIG_CLIENT")
                .passwordEncoder(passwordEncoder::encode)
                .build();

        UserDetails actuatorClient = User.builder()
                .username("actuatorClient")
                .password("actuatorClient")
                .roles("ACTUATOR_CLIENT")
                .passwordEncoder(passwordEncoder::encode)
                .build();

        return new InMemoryUserDetailsManager(configClient, actuatorClient);
    }
}