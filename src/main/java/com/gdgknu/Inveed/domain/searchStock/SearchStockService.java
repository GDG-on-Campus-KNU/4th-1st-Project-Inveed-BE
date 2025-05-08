package com.gdgknu.Inveed.domain.searchStock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchStockService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    String indexName = getTodayIndexName();
    String elasticsearchUrl = "http://localhost:9200/" + indexName + "/_search";

    public List<SearchKeywordDTO> getTop10Keywords() {
        String query = """
            {
              "size": 0,
              "aggs": {
                "top_keywords": {
                  "terms": {
                    "field": "message.keyword",
                    "size": 10,
                    "order": { "_count": "desc" }
                  }
                }
              }
            }
        """;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(query, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                elasticsearchUrl,
                HttpMethod.POST,
                request,
                String.class
        );

        try {
            JsonNode root = objectMapper.readTree(response.getBody());
            JsonNode buckets = root.path("aggregations").path("top_keywords").path("buckets");

            List<SearchKeywordDTO> results = new ArrayList<>();
            for (JsonNode bucket : buckets) {
                String keyword = bucket.path("key").asText();
                long count = bucket.path("doc_count").asLong();
                results.add(new SearchKeywordDTO(keyword, count));
            }
            return results;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse Elasticsearch response", e);
        }
    }
    private String getTodayIndexName() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        return "search-logs-" + today.format(formatter);
    }

}
