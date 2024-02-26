package com.owl.fitness_service.handlers;


import com.owl.fitness_service.repository.db.services.ArticleService;
import com.owl.fitness_service.repository.entites.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.UUID;


@Component
public class ArticleHandler {
    protected ArticleService articleService;

    @Autowired
    public ArticleHandler(ArticleService articleService) {
        this.articleService = articleService;
    }

    public Mono<ServerResponse> allArticles(ServerRequest request) {
        return ServerResponse.ok()
                .headers(headers ->
                        headers.add(
                                HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN,
                                request.headers().header("Origin").iterator().next()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(Flux.fromIterable(articleService.allArticles()), Article.class);
    }

    public Mono<ServerResponse> articleById(ServerRequest request) {
        Article article;

        if (Objects.nonNull(String.valueOf(request.attribute("articleId")))) {
            article = articleService.articleById(
                    UUID.fromString(
                            String.valueOf(request.attribute("articleId"))));
            return ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(article, Article.class);
        }

        return ServerResponse.noContent()
                .build();
    }
}