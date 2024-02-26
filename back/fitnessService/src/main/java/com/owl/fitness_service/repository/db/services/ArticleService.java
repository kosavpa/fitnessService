package com.owl.fitness_service.repository.db.services;


import com.owl.fitness_service.repository.entites.Article;

import java.util.List;
import java.util.UUID;

public interface ArticleService {
    List<Article> allArticles();

    Article articleById(UUID id);

    void saveArticle(Article article);
}