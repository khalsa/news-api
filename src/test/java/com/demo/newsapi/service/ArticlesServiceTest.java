package com.demo.newsapi.service;

import com.demo.newsapi.dao.NewsApiDao;
import com.demo.newsapi.model.NewsApiResponse;
import com.demo.newsapi.model.NewsArticle;
import com.demo.newsapi.model.Params;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ArticlesServiceTest {

    @InjectMocks
    private ArticlesService articlesService;

    @Mock
    private NewsApiDao newsApiDao;

    @Test
    void testSearch() {
        Params p = Params.builder().query("apple").count(1).build();
        NewsArticle a = new NewsArticle();
        a.setContent("apple iphone 15 launched");
        a.setAuthor("apple");
        NewsApiResponse expectedResponse = new NewsApiResponse();
        List<NewsArticle> articlesList = new ArrayList<>();
        articlesList.add(a);
        expectedResponse.setArticles(articlesList);
        expectedResponse.setStatus("ok");
        when(newsApiDao.searchArticles(p)).thenReturn(expectedResponse);
        NewsApiResponse actualNewsApiResponse = articlesService.search(p);
        assertEquals(actualNewsApiResponse, expectedResponse);
    }

    @Test
    void testGetTopNews() {
        Params p = Params.builder().count(1).build();
        NewsArticle a = new NewsArticle();
        a.setContent("Usa won the football worldcup");
        a.setAuthor("sports10");
        NewsApiResponse expectedResponse = new NewsApiResponse();
        List<NewsArticle> articlesList = new ArrayList<>();
        articlesList.add(a);
        expectedResponse.setArticles(articlesList);
        expectedResponse.setStatus("ok");
        when(newsApiDao.getTopArticles(p)).thenReturn(expectedResponse);
        NewsApiResponse actualNewsApiResponse = articlesService.getTopNews(p);
        assertEquals(actualNewsApiResponse, expectedResponse);
    }
}
