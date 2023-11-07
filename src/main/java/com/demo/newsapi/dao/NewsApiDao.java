package com.demo.newsapi.dao;

import com.demo.newsapi.client.NewsApiClient;
import com.demo.newsapi.exception.ApplicationException;
import com.demo.newsapi.model.NewsApiResponse;
import com.demo.newsapi.model.Params;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;


/*
 *Provides data to the service layer. In this case calls an external api.
 */
@Service
@Slf4j
public class NewsApiDao {

    @Autowired
    NewsApiClient newsApiClient;

    /**
     * @param p Query params for search
     * @return NewsApiResponse from external service
     */
    @Cacheable(value="articles")
    @Scheduled(fixedDelay = 12, timeUnit = TimeUnit.HOURS)
    public NewsApiResponse searchArticles(Params p) {
        try {
            return newsApiClient.searchNews(getSearchQuery(p));
        } catch (RestClientException e) {
            throw new ApplicationException("exception occured while calling news api service " + e.getMessage(), e);
        }
    }

    /**
     * @param p Query params for getting top articles
     * @return NewsApiResponse from external service
     */
    @Cacheable(value="articles")
    @Scheduled(fixedDelay = 12, timeUnit = TimeUnit.HOURS)
    public NewsApiResponse getTopArticles(Params p) {
        try {
            return newsApiClient.getTopNews(getTopResultsQuery(p));
        } catch (RestClientException e) {
            throw new ApplicationException("exception occured while calling news api service " + e.getMessage(), e);
        }
    }

    /**
     * @param value query string for encoding
     * @return encoded url
     */
    private String encode(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        }
        catch (UnsupportedEncodingException ex) {
            log.error("exception occured while decoding url params " + ex.getMessage());
            return value;
        }
    }

    /**
     * @param p Query params
     * @return query string based on input param
     */
    private String getSearchQuery(Params p) {
        StringBuffer sb = new StringBuffer();
        if (p.getQuery() != null) {
            String query = p.getQuery();
            if (query.contains("AND") || query.contains("OR") || query.contains("NOT")) {
                query = encode(query);
            }
            sb.append("q="+query);
        }
        if (p.getSearchIn() != null) {
            sb.append("&searchIn=" + p.getSearchIn());
        }
        Integer count = p.getCount() == null ? 10 : p.getCount();
        sb.append("&pageSize="+count);
        return sb.toString();
    }

    /**
     * @param p Query params
     * @return query string based on input param
     */
    private String getTopResultsQuery(Params p) {
        StringBuffer sb = new StringBuffer();
        if (p.getCategory() != null) {
            sb.append("category="+p.getCategory()+"&");
        }
        Integer count = p.getCount() == null ? 10 : p.getCount();
        sb.append("pageSize="+count);
        return sb.toString();
    }
}
