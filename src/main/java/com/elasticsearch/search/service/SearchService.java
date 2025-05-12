package com.elasticsearch.search.service;

import co.elastic.clients.elasticsearch.core.search.Hit;
import com.elasticsearch.search.api.model.Result;
import com.elasticsearch.search.api.model.Results;
import com.elasticsearch.search.domain.EsClient;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {
    private final EsClient esClient;

    public SearchService(EsClient esClient) {
        this.esClient = esClient;
    }

    public Results search(String query, Integer page) {
        //TODO insert page
        var searchResponse = esClient.search(query, page);

        List<Hit<ObjectNode>> hits = searchResponse.hits().hits();

        var resultsList = hits.stream().map(h ->
            new Result()
                .content(treatContent(h.source().get("content").asText()))
                .title(h.source().get("title").asText())
                .url(h.source().get("url").asText())
        ).collect(Collectors.toList());
        Results results = new Results();
        results.setResultsList(resultsList);

        results.setPage(page.longValue());
        results.setTotalResults(searchResponse.hits().total().value());
        return results;
    }

    private String treatContent(String content) {
        content = content.replaceAll("</?(som|math)\\d*>", "");
        content = content.replaceAll("[^A-Za-z\\s\\d]+", "");
        content = content.replaceAll("\\s+", " ");
        content = content.replaceAll("^\\s+", "");
        return content;
    }
}
