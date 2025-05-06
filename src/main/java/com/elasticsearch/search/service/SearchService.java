package com.elasticsearch.search.service;

import com.elasticsearch.search.api.model.Results;
import com.elasticsearch.search.domain.EsClient;
import org.springframework.stereotype.Service;

@Service
public class SearchService {
    private final EsClient esClient;

    public SearchService(EsClient esClient) {
        this.esClient = esClient;
    }

    public Results search(String query, Integer page) {
        //TODO insert page
        var searchResponse = esClient.search(query);

        //TODO populate results with searchResponse values
        Results results = new Results();
        return results;
    }
}
