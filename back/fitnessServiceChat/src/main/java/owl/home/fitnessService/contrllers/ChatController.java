package owl.home.fitnessService.contrllers;


import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import owl.home.fitnessService.entity.Message;
import owl.home.fitnessService.service.MessageService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;


@Component
public class ChatController implements WebSocketHandler {
    private final Sinks.Many<Message> sinks;

    private final MessageService messageService;

    @Autowired
    public ChatController(MessageService messageService) {
        this.messageService = messageService;
        this.sinks = Sinks.many().multicast().onBackpressureBuffer();
    }

    @Nonnull
    @Override
    public Mono<Void> handle(@Nonnull WebSocketSession session) {
        Mono<Void> inputMessages = session
                .receive()
                .map(WebSocketMessage::getPayloadAsText)
                .map(messageService::saveMessage)
                .doOnNext(sinks::tryEmitNext)
                .then();

        Flux<WebSocketMessage> messagesFlux = sinks
                .asFlux()
                .map(message -> session.textMessage(message.toString()));

        return Mono.zip(inputMessages, session.send(messagesFlux)).then();
    }
}