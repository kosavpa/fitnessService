package owl.home.fitnessService.contrllers;

import org.springframework.web.reactive.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ControllerUtils {
    protected static Map<String, WebSocketSession> tokenRSocketRequesterMap = new ConcurrentHashMap<>();


}