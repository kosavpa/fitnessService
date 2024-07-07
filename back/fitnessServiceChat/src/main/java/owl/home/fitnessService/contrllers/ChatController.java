package owl.home.fitnessService.contrllers;


import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;


@Component
public class ChatController implements WebSocketHandler {
    private final Sinks.Many<String> sinks = Sinks.many().multicast().onBackpressureBuffer();

    @Nonnull
    @Override
    public Mono<Void> handle(@Nonnull WebSocketSession session) {
        Mono<Void> inputMessages = session
                .receive()
                .map(WebSocketMessage::getPayloadAsText)
                .doOnNext(sinks::tryEmitNext)
                .then();

        Flux<WebSocketMessage> messagesFlux = sinks
                .asFlux()
                .map(session::textMessage);

        return Mono.zip(inputMessages, session.send(messagesFlux)).then();
    }
}