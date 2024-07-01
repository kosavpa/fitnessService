package owl.home.fitnessService.contrllers;


import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import owl.home.fitnessService.service.JwtService;
import owl.home.fitnessService.service.MessageService;
import reactor.core.publisher.Mono;


@Component
public class ChatController implements WebSocketHandler {
    private JwtService jwtService;

    private MessageService messageService;

    @Nonnull
    @Override
    public Mono<Void> handle(@Nonnull WebSocketSession session) {
        ControllerUtils.tokenRSocketRequesterMap.put(session.getId(), session);

        return session
                .receive()
                .doOnNext(message -> ControllerUtils.tokenRSocketRequesterMap
                        .values()
                        .forEach(webSocketSession -> webSocketSession.send(Mono.just(message))))
                .concatMap(message -> Mono.just(messageService.saveMessage(message)))
                .then();
    }
}