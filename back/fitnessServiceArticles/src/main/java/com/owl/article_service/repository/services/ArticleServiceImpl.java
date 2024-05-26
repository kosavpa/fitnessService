package com.owl.article_service.repository.services;

import com.owl.article_service.repository.entities.Article;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Service("articleService")
public class ArticleServiceImpl implements ArticleService {
    private final EntityManager entityManager;

    @Autowired
    public ArticleServiceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Article> allArticles() {
        return entityManager.createQuery("select a from Article a", Article.class)
                .getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Article articleById(UUID id) {
        return entityManager
                .createQuery("select a from Article a where a.id = :id", Article.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public void saveArticle(Article article) {
        entityManager.persist(article);
        entityManager.flush();
    }
}