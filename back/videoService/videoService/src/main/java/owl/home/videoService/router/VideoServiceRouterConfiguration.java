package owl.home.videoService.router;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import owl.home.videoService.handler.ImageHandler;
import owl.home.videoService.handler.VideoHandler;
import owl.home.videoService.handler.VideoInfoHandler;


@Configuration
public class VideoServiceRouterConfiguration {
    @Bean
    public RouterFunction<ServerResponse> getVideo(VideoHandler handlerConfiguration) {
        return RouterFunctions
                .route(
                        RequestPredicates
                                .GET("/vid/{videoDir}/{videoName}")
                                .and(RequestPredicates.accept(MediaType.APPLICATION_OCTET_STREAM)),
                        handlerConfiguration::getRange);
    }

    @Bean
    public RouterFunction<ServerResponse> getInfo(VideoInfoHandler informationHandler) {
        return RouterFunctions
                .route(
                        RequestPredicates
                                .GET("/videos-info")
                                .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                        request -> informationHandler.getResourceInfo());
    }

    @Bean
    public RouterFunction<ServerResponse> getImage(ImageHandler imageHandler) {
        return RouterFunctions
                .route(
                        RequestPredicates
                                .GET("/img/{videoDir}/{videoName}")
                                .and(RequestPredicates.accept(MediaType.IMAGE_JPEG)),
                        imageHandler::getImage);
    }
}