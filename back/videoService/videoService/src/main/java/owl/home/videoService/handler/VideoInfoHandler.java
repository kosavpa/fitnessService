package owl.home.videoService.handler;


import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import owl.home.videoService.videowrapper.ResourceInfo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Component
public class VideoInfoHandler {
    public Mono<ServerResponse> getResourceInfo() {
        return ServerResponse
                .ok()
                .body(Flux.fromIterable(ResourceInfo.getInfos()), ResourceInfo.InfoWrapper.class);
    }
}