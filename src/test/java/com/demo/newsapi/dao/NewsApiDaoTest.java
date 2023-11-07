package com.demo.newsapi.dao;

import com.demo.newsapi.client.NewsApiClient;
import com.demo.newsapi.model.NewsApiResponse;
import com.demo.newsapi.model.NewsArticle;
import com.demo.newsapi.model.Params;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.invokeMethod;

@ExtendWith(MockitoExtension.class)
public class NewsApiDaoTest {

    @Mock
    NewsApiClient newsApiClient;

    @InjectMocks
    private NewsApiDao newsApiDao;

    @Test
    void testSearchArticles() {
        Params p = Params.builder().query("apple").searchIn("title").count(1).build();
        NewsArticle a = new NewsArticle();
        a.setContent("apple iphone 15 launched");
        a.setAuthor("apple");
        NewsApiResponse expectedResponse = new NewsApiResponse();
        List<NewsArticle> articlesList = new ArrayList<>();
        articlesList.add(a);
        expectedResponse.setArticles(articlesList);
        expectedResponse.setStatus("ok");
        // Invoking private method to get the search url
        String searchQuery = invokeMethod(newsApiDao, "getSearchQuery", p);
        when(newsApiClient.searchNews(searchQuery)).thenReturn(expectedResponse);
        // Mocking response from newsApiDao searchArticles method call
        NewsApiResponse actualNewsApiResponse = newsApiDao.searchArticles(p);
        assertEquals(actualNewsApiResponse, expectedResponse);
        assertEquals(searchQuery,"q=apple&searchIn=title&pageSize=1");
    }

    @Test
    void testGetTopArticles() {
        Params p = Params.builder().count(1).build();
        NewsArticle a = new NewsArticle();
        a.setContent("Usa won the football worldcup");
        a.setAuthor("sports10");
        NewsApiResponse expectedResponse = new NewsApiResponse();
        List<NewsArticle> articlesList = new ArrayList<>();
        articlesList.add(a);
        expectedResponse.setArticles(articlesList);
        expectedResponse.setStatus("ok");
        // Invoking private method to get the search url
        String searchQuery = invokeMethod(newsApiDao, "getTopResultsQuery", p);
        // Mocking response from newsApiDao getTopNews method call
        when(newsApiClient.getTopNews(searchQuery)).thenReturn(expectedResponse);
        NewsApiResponse actualNewsApiResponse = newsApiDao.getTopArticles(p);
        // asserting mocked vs actual
        assertEquals(actualNewsApiResponse, expectedResponse);
        assertEquals(searchQuery,"pageSize=1");
    }
}
