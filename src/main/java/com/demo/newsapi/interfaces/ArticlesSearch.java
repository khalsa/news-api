package com.demo.newsapi.interfaces;

import com.demo.newsapi.model.NewsApiResponse;
import com.demo.newsapi.model.Params;

/*
 * Interface lists all the different kind of search the application supports.
 */
public interface ArticlesSearch {
    public NewsApiResponse search(Params params);
    public NewsApiResponse getTopNews(Params params);
    public NewsApiResponse getTopNewsForCategory(Params params);
}
