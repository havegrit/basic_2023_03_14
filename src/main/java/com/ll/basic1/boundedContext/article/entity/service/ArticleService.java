package com.ll.basic1.boundedContext.article.entity.service;

import com.ll.basic1.boundedContext.article.entity.Article;
import com.ll.basic1.boundedContext.article.entity.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public Article write(String title, String body) {
        Article newArticle = Article
                .builder()
                .create_date(LocalDateTime.now())
                .modify_date(LocalDateTime.now())
                .title(title)
                .body(body)
                .build();
        articleRepository.save(newArticle);

        return newArticle;
    }
}
