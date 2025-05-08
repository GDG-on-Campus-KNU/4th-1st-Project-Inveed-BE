package com.gdgknu.Inveed.domain.searchStock;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/logs")
public class SearchStockController {

    private final SearchStockService searchStockService;
    private static final Logger logger = Logger.getLogger("SearchLogger");

    @PostMapping("/search")
    public ResponseEntity<String> logSearchKeyword(@RequestBody Map<String, String> body) {
        String keyword = body.get("keyword");
        if (keyword == null || keyword.isBlank()) {
            return ResponseEntity.badRequest().body("Missing 'keyword'");
        }

        logger.info(keyword);
        return ResponseEntity.ok("Logged keyword: " + keyword);
    }

    @GetMapping("/top10")
    public ResponseEntity<List<SearchKeywordDTO>> getTop10Keywords() {
        List<SearchKeywordDTO> top10Keywords = searchStockService.getTop10Keywords();
        return ResponseEntity.ok(top10Keywords);
    }

}
