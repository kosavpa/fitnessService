package com.owl.article_service.repository.db.repositories;


import com.owl.article_service.repository.entites.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository("articleRepo")
public interface ArticleRepository extends JpaRepository<Article, UUID> {
}