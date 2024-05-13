package com.owl.config_service.config.security;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManagerAdapter;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.security.web.server.savedrequest.NoOpServerRequestCache;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;


@SuppressWarnings("unused")
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    @Value("${allowOrigin}")
    private String allowOrigin;

    @Bean("webFilterChain")
    public SecurityWebFilterChain getMainConfig(ServerHttpSecurity httpSecurity) {
        return httpSecurity.authorizeExchange(auth -> auth
                        .pathMatchers("/actuator/**")
                        .hasRole("ACTUATOR_CLIENT")
                        .pathMatchers("/**")
                        .hasRole("CONFIG_CLIENT"))
                .cors(this::configureCors)
                .httpBasic(httpBasicSpec ->
                        httpBasicSpec.securityContextRepository(NoOpServerSecurityContextRepository.getInstance()))
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .requestCache(requestCacheSpec -> requestCacheSpec.requestCache(NoOpServerRequestCache.getInstance()))
                .build();
    }

    private void configureCors(ServerHttpSecurity.CorsSpec corsSpec) {
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

    @Bean("simpleUserDetails")
    public MapReactiveUserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
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
        return new MapReactiveUserDetailsService(configClient, actuatorClient);
    }

    @Bean("passwordEncoder")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}