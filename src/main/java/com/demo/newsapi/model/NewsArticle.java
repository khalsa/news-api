package com.demo.newsapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class NewsArticle extends Article {
    private String sourceId;
    private String sourceName;
    private String urlToImage;
    private String url;

    @JsonProperty("source")
    private void unpackFromNestedObject(Map<String, String> source) {
        sourceId = source.get("id");
        sourceName = source.get("name");
    }
}
