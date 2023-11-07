package com.demo.newsapi.model;

import lombok.Data;

@Data
public class Article {
    private String title;
    private String description;
    private String publishedAt;
    private String content;
    private String author;
}
