package com.owl.article_service.repository.services;


import com.owl.article_service.repository.entity.Article;

import java.util.List;
import java.util.UUID;

public interface ArticleService {
    List<Article> allArticles();

    Article articleById(UUID id);

    void saveArticle(Article article);
}