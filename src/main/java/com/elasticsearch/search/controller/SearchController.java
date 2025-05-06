package com.elasticsearch.search.controller;

import com.elasticsearch.search.api.facade.SearchApi;
import com.elasticsearch.search.api.model.Results;
import com.elasticsearch.search.service.SearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
public class SearchController implements SearchApi {
    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @Override
    public CompletableFuture<ResponseEntity<Results>> search(String q, Integer p) {
        var result = searchService.search(q, p);
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(result));
    }
}
