package com.owl.article_service.routers;


import com.owl.article_service.handlers.ArticleHandler;
import com.owl.article_service.handlers.ImageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;


@SuppressWarnings("unused")
@Configuration(proxyBeanMethods = false)
public class Routers {
    @Bean("allArticles")
    public RouterFunction<ServerResponse> getAllArticleRouter(ArticleHandler handler) {
        return RouterFunctions.route(RequestPredicates.GET("/articles")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                handler::allArticles);
    }

    @Bean("articleById")
    public RouterFunction<ServerResponse> getArticleByIdRouter(ArticleHandler handler) {
        return RouterFunctions.route(RequestPredicates.GET("/article")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                handler::articleById);
    }

    @Bean("articleImage")
    public RouterFunction<ServerResponse> getImgByPathRouter(ImageHandler handler) {
        return RouterFunctions.route(RequestPredicates.GET("/img/{relativePath}")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_OCTET_STREAM)),
                handler::getImgByPath);
    }
}