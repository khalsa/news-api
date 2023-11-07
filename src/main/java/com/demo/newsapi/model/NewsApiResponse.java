package com.demo.newsapi.model;

import lombok.Data;

import java.util.List;

@Data
public class NewsApiResponse {
    private String status;
    private List<NewsArticle> articles;
}
