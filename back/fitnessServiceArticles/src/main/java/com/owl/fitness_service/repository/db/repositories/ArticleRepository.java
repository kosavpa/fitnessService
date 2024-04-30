package com.owl.fitness_service.repository.db.repositories;


import com.owl.fitness_service.repository.entites.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface ArticleRepository extends JpaRepository<Article, UUID> {
}