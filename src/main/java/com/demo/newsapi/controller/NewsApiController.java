package com.demo.newsapi.controller;

import com.demo.newsapi.model.NewsApiResponse;
import com.demo.newsapi.model.Params;
import com.demo.newsapi.service.ArticlesService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/v1/news")
@CrossOrigin
@Validated
public class NewsApiController {

    @Autowired
    ArticlesService newsApiService;

    /**
     * @param q input keywords or phrases to search
     * @param searchIn the fields to restrict your q search to. Possible values are title, description, content
     * @param count limit the number of records returned, if not specified default is 10
     * @return
     */
    @GetMapping(value={"/search"})
    @ResponseStatus(HttpStatus.OK)
    public NewsApiResponse search(@RequestParam(name = "q") @Size(max = 100) String q,
                                  @RequestParam(name = "searchIn", required = false) @Size(max = 30) String searchIn,
                                  @RequestParam(name = "count", required = false) @Max(100) Integer count) {
        return newsApiService.search(Params.builder().query(q).searchIn(searchIn).count(count).build());
    }

    /**
     * @param count the number of records returned
     * @return
     */
    @GetMapping(value={"/search/top/{count}"})
    @ResponseStatus(HttpStatus.OK)
    public NewsApiResponse getTopNews(@PathVariable(name = "count") @Min(1) @Max(100) Integer count) {
        return newsApiService.getTopNews(Params.builder().count(count).build());
    }

    /**
     * @param category possible categories business, entertainment, general, health, technology
     * @param count the number of records returned
     * @return
     */
    @GetMapping(value={"/search/top/{category}/{count}"})
    @ResponseStatus(HttpStatus.OK)
    public NewsApiResponse getTopNewsForCategory(@PathVariable("category") @Size(max = 20) String category,
                                                 @PathVariable(name = "count") @Min(1) @Max(100) Integer count) {
        return newsApiService.getTopNewsForCategory(Params.builder().category(category).count(count).build());
    }

    @GetMapping("/ping")
    @ResponseStatus(HttpStatus.OK)
    public String ping() {
        return "Hello world";
    }
}
