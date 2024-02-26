package com.owl.fitness_service.repository.db.services;

import com.owl.fitness_service.repository.db.repositories.ArticleRepository;
import com.owl.fitness_service.repository.entites.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class ArticleServiceImpl implements ArticleService {
    protected ArticleRepository repository;

    @Autowired
    public ArticleServiceImpl(ArticleRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Article> allArticles() {
        return repository.findAll();
    }

    @Override
    public Article articleById(UUID id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void saveArticle(Article article) {
        repository.save(article);
    }
}