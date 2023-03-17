package com.ll.basic1.boundedContext.article.entity.repository;

import com.ll.basic1.boundedContext.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public class ArticleRepository extends JpaRepository<Article, Long> {
}
