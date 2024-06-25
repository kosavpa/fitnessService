package owl.home.videoService.handler;


import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import owl.home.videoService.videowrapper.ResourceProvider;
import owl.home.videoService.videowrapper.ResourceType;
import reactor.core.publisher.Mono;


@Component
public class ImageHandler {
    public Mono<ServerResponse> getImage(ServerRequest request) {
        return ServerResponse
                .status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_JPEG)
                .body(Mono.just(ResourceProvider.requestToResource(request, ResourceType.IMAGE)), Resource.class);
    }
}