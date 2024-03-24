package com.owl.fitness_service;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManagerAdapter;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;


@SuppressWarnings("unused")
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain test(ServerHttpSecurity httpSecurity,
                                       ReactiveAuthenticationManager authManager) {
        return httpSecurity.authorizeExchange(auth -> auth
                        .pathMatchers("/")
                        .permitAll()
                        .pathMatchers("/user")
                        .hasAnyRole("USER", "ADMIN")
                        .pathMatchers("/admin")
                        .hasAnyRole("ADMIN"))
                .httpBasic(httpBasicCustomizer -> httpBasicCustomizer.authenticationManager(authManager))
                .build();
    }

    @Bean
    public ReactiveAuthenticationManager manager(UserDetailsService userDetailsService,
                                                 PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ReactiveAuthenticationManagerAdapter(new ProviderManager(authenticationProvider));
    }
}