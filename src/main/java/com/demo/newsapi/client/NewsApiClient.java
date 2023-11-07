package com.demo.newsapi.client;

import com.demo.newsapi.model.NewsApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.demo.newsapi.encryption.AESEncryptionDecryption.decrypt;

/*
 * Rest client responsible for calling external news api service
 */
@Slf4j
@Service
public class NewsApiClient {
    @Value("${news.api.baseurl}")
    private String baseUrl;
    @Value("${news.api.key}")
    private String apiKey;
    @Autowired
    RestTemplate restTemplate;

    public NewsApiResponse getTopNews(String query) {
        String decryptedKey = apiKey == null ? "" : decrypt(apiKey,"api-key");
        String topHeadlinesUrl = baseUrl + "/top-headlines?"+query+"&country=us&apiKey="+decryptedKey;
        log.info("calling external api with url :: " + topHeadlinesUrl);
        return restTemplate.getForObject(topHeadlinesUrl, NewsApiResponse.class);
    }

    public NewsApiResponse searchNews(String query) {
        String decryptedKey = apiKey == null ? "" : decrypt(apiKey,"api-key");
        String searchUrl = baseUrl + "/everything?"+query+"&sortBy=popularity&apiKey="+decryptedKey;
        log.info("calling external api with url :: " + searchUrl);
        return restTemplate.getForObject(searchUrl, NewsApiResponse.class);
    }
}
