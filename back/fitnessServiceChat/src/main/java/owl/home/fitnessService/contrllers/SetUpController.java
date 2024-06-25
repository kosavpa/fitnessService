package owl.home.fitnessService.contrllers;


import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.annotation.ConnectMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;


@Controller
public class SetUpController {
    @ConnectMapping("chat")
    public Mono<Void> handleSetUp(RSocketRequester requester, @Payload String token) {
        ControllerUtils.tokenRSocketRequesterMap.put(token, requester);

        return Mono.empty();
    }
}