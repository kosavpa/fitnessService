package owl.home.fitnessService.contrllers;

import org.springframework.messaging.rsocket.RSocketRequester;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ControllerUtils {
    protected static Map<String, RSocketRequester> tokenRSocketRequesterMap = new ConcurrentHashMap<>();


}