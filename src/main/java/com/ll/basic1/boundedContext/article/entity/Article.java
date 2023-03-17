package com.ll.basic1.boundedContext.article.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Builder
public class Article {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;
    private LocalDateTime create_date;
    private LocalDateTime modify_date;
    private String title;
    private String body;

}
