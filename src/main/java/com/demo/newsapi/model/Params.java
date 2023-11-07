package com.demo.newsapi.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Params {
    private Integer count;
    private String query;
    private String searchIn;
    private String category;
 }
