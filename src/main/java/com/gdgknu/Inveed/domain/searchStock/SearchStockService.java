package com.gdgknu.Inveed.domain.searchStock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class SearchStockService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private static final Logger logger = Logger.getLogger("SearchLogger");

    @Value("${spring.elasticsearch.uris}")
    private String baseUrl;


    public List<SearchKeywordDTO> getTop10Keywords() {
        String indexName = getTodayIndexName();
        String elasticsearchUrl = baseUrl + indexName + "/_search";
        String query = """
            {
              "size": 0,
              "aggs": {
                "top_keywords": {
                  "terms": {
                    "field": "message",
                    "size": 10,
                    "order": { "_count": "desc" }
                  }
                }
              }
            }
        """;

        ResponseEntity<String> response = sendRequest(elasticsearchUrl, query);

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
    public List<String> searchByKeyword(String keyword) {

        if (keyword == null || keyword.isBlank()) {
            throw new IllegalArgumentException("Keyword cannot be null or blank");
        }

        String indexName = getTodayIndexName();
        String elasticsearchUrl = baseUrl + indexName + "/_search";

        String query = """
            {
              "query": {
                "match": {
                  "message": {
                    "query": "%s"
                  }
                }
              }
            }
        """.formatted(keyword);

        ResponseEntity<String> response = sendRequest(elasticsearchUrl, query);

        try {
            JsonNode root = objectMapper.readTree(response.getBody());
            JsonNode hits = root.path("hits").path("hits");

            List<String> results = new ArrayList<>();
            for (JsonNode hit : hits) {
                String message = hit.path("_source").path("message").asText();
                // 중복 제거
                if (results.contains(message)) {
                    continue;
                }
                results.add(message);
            }

            return results;

        } catch (Exception e) {
            throw new RuntimeException("Failed to search by keyword", e);
        }
    }
    public void logKeyword(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            throw new IllegalArgumentException("Keyword cannot be null or blank");
        }

        logger.info(keyword);
    }
    private String getTodayIndexName() {
        LocalDateTime today = LocalDateTime.now(ZoneId.of("Asia/Seoul")).minusHours(9);
        System.out.println("Today: " + today);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        return "search-logs-" + today.format(formatter);
    }

    private ResponseEntity<String> sendRequest(String url, String query) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(query, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    request,
                    String.class);
            if (response.getStatusCode() != HttpStatus.OK) {
                throw new RuntimeException("Failed to get response from Elasticsearch");
            }
            return response;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            throw new RuntimeException("Failed to send request", e);
        }
    }
}
