package owl.home.videoService.handler;


import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import owl.home.videoService.videowrapper.ResourceProvider;
import owl.home.videoService.videowrapper.ResourceType;
import reactor.core.publisher.Mono;


@Component
public class VideoHandler {
    public Mono<ServerResponse> getRange(ServerRequest request) {
        return ServerResponse
                .status(HttpStatus.PARTIAL_CONTENT)
                .body(Mono.just(ResourceProvider.requestToResource(request, ResourceType.VIDEO)), Resource.class);
    }
}