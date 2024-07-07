package owl.home.fitnessService.config.handler;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import owl.home.fitnessService.contrllers.ChatController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Configuration
public class HandlerMappingConfiguration {
    @Bean
    public HandlerMapping handlerMapping(ChatController chatController) {
        Map<String, WebSocketHandler> map = new HashMap<>();

        map.put("/chat", chatController);

        return new SimpleUrlHandlerMapping(map, 1);
    }

    @Bean
    public CorsWebFilter configureCors() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowedOrigins(List.of("*"));
        corsConfiguration.setAllowedMethods(List.of("*"));
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addExposedHeader(HttpHeaders.LOCATION);

        source.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsWebFilter(source);
    }
}