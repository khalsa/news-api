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

    @Cacheable(value="articles")
    @Scheduled(fixedDelay = 12, timeUnit = TimeUnit.HOURS)
    public NewsApiResponse searchArticles(Params p) {
        try {
            return newsApiClient.searchNews(getSearchQuery(p));
        } catch (Exception e) {
            throw new ApplicationException("exception occured while calling news api service " + e.getMessage(), e);
        }
    }

    @Cacheable(value="articles")
    @Scheduled(fixedDelay = 12, timeUnit = TimeUnit.HOURS)
    public NewsApiResponse getTopArticles(Params p) {
        try {
            return newsApiClient.getTopNews(getTopResultsQuery(p));
        } catch (Exception e) {
            throw new ApplicationException("exception occured while calling news api service " + e.getMessage(), e);
        }
    }

    private String encode(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        }
        catch (Exception ex) {
            log.error("exception occured while decoding url params " + ex.getMessage());
            return value;
        }
    }

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
