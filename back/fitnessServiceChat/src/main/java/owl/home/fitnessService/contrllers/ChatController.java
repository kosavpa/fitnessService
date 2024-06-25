package owl.home.fitnessService.contrllers;


import org.springframework.messaging.rsocket.annotation.ConnectMapping;
import org.springframework.stereotype.Controller;
import owl.home.fitnessService.entity.Message;
import owl.home.fitnessService.service.JwtService;
import reactor.core.publisher.Mono;


@Controller
public class ChatController {
    protected JwtService jwtService;

    @ConnectMapping("chat")
    public Mono<Void> sendMessageForAll(Mono<Message> message) {
        message.doOnNext(m -> ControllerUtils.tokenRSocketRequesterMap
                .values()
                .forEach(requester -> requester
                        .route("chat")
                        .data(message, Message.class)
                        .send()));

        return Mono.empty();
    }
}