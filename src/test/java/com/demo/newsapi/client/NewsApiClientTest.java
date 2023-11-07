package com.demo.newsapi.client;
import com.demo.newsapi.model.NewsApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@ExtendWith(MockitoExtension.class)
public class NewsApiClientTest {

    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    private NewsApiClient newsApiClient;

    @BeforeEach
    void init() {
        final NewsApiResponse response = new NewsApiResponse();
        when(this.restTemplate.getForObject(anyString(), any())).thenReturn(response);
    }

    @Test
    void testSearchNews() {
        NewsApiResponse expectedResponse = new NewsApiResponse();
        // Setting up private property.
        setField(newsApiClient, "baseUrl", "https://newsapi.org/v2");
        NewsApiResponse actualResponse = newsApiClient.searchNews("test");
        verify(restTemplate, times(1)).getForObject(
                ArgumentMatchers.eq("https://newsapi.org/v2/everything?test&sortBy=popularity&apiKey="),
                ArgumentMatchers.eq(NewsApiResponse.class)
        );
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void testGetTopNews() {
        NewsApiResponse expectedResponse = new NewsApiResponse();
        // Setting up private property.
        setField(newsApiClient, "baseUrl", "https://newsapi.org/v2");
        NewsApiResponse actualResponse = newsApiClient.getTopNews("test");
        verify(restTemplate, times(1)).getForObject(
                ArgumentMatchers.eq("https://newsapi.org/v2/top-headlines?test&country=us&apiKey="),
                ArgumentMatchers.eq(NewsApiResponse.class)
        );
        assertEquals(expectedResponse, actualResponse);
    }
}
