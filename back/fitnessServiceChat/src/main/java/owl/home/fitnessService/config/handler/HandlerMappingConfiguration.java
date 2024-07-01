package owl.home.fitnessService.config.handler;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import owl.home.fitnessService.contrllers.ChatController;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class HandlerMappingConfiguration {
    @Bean
    public HandlerMapping handlerMapping(ChatController chatController) {
        Map<String, WebSocketHandler> map = new HashMap<>();

        map.put("/chat", chatController);

        return new SimpleUrlHandlerMapping(map, 1);
    }
}