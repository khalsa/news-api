package com.demo.newsapi.service;

import com.demo.newsapi.dao.NewsApiDao;
import com.demo.newsapi.interfaces.ArticlesSearch;
import com.demo.newsapi.model.NewsApiResponse;
import com.demo.newsapi.model.Params;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * ArticlesService class is responsible for the implementation of the searching of the articles
 * It supports searching based on search params and getting top articles from the news api.
 */
@Service
public class ArticlesService implements ArticlesSearch {
    @Autowired
    NewsApiDao newsApiDao;

    /**
     * @param params Input query params for searching
     * @return NewsApiResponse from dao
     */
    @Override
    public NewsApiResponse search(Params params) {
        return newsApiDao.searchArticles(params);
    }

    /**
     * @param params Input query params for getting top n news
     * @return NewsApiResponse from dao
     */
    @Override
    public NewsApiResponse getTopNews(Params params) {
        return newsApiDao.getTopArticles(params);
    }

    /**
     * @param params Input query params for getting top n news based on the category
     * @return NewsApiResponse from dao
     */
    @Override
    public NewsApiResponse getTopNewsForCategory(Params params) {
        return newsApiDao.getTopArticles(params);
    }
}
