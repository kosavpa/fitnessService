package com.owl.fitness_service.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManagerAdapter;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.web.server.ServerHttpSecurity;
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
public class SecurityConfig {
    @Value("${allowOrigin}")
    private String allowOrigin;

    @Bean("webFilterChain")
    public SecurityWebFilterChain getMainConfig(ServerHttpSecurity httpSecurity,
                                       ReactiveAuthenticationManager authManager) {
        return httpSecurity.authorizeExchange(auth -> auth
                        .pathMatchers(HttpMethod.POST, "/signup")
                        .permitAll()
                        .pathMatchers(HttpMethod.GET, "/signin")
                        .authenticated())
                .cors(this::configureCors)
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(httpBasicCustomizer -> httpBasicCustomizer.authenticationManager(authManager))
                .requestCache(requestCacheSpec -> requestCacheSpec.requestCache(NoOpServerRequestCache.getInstance()))
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
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

    @Bean("daoAuthManager")
    public ReactiveAuthenticationManager manager(UserDetailsService userDetailsService,
                                                 PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ReactiveAuthenticationManagerAdapter(new ProviderManager(authenticationProvider));
    }

    @Bean("passwordEncoder")
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}