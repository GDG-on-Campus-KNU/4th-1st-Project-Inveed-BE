package com.gdgknu.Inveed.domain.searchStock;

import com.gdgknu.Inveed.response.ResponseUtil;
import com.gdgknu.Inveed.response.SuccessCode;
import com.gdgknu.Inveed.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/logs")
public class SearchStockController {

    private final SearchStockService searchStockService;

    @PostMapping("/search_keyword")
    public ResponseEntity<SuccessResponse<List<String>>> SearchKeyword(@RequestBody Map<String, String> body) {
        List<String> results = searchStockService.searchByKeyword(body.get("keyword"));
        return ResponseUtil.buildSuccessResponse(SuccessCode.LOG_SEARCH_SUCCESS, results);
    }

    @GetMapping("/get_top10_keywords")
    public ResponseEntity<SuccessResponse<List<SearchKeywordDTO>>> getTop10Keywords() {
        List<SearchKeywordDTO> top10Keywords = searchStockService.getTop10Keywords();
        return ResponseUtil.buildSuccessResponse(SuccessCode.TOP10_KEYWORD_SUCCESS, top10Keywords);
    }

    @PostMapping("/log_keyword")
    public ResponseEntity<SuccessResponse<Object>> logKeyword(@RequestBody Map<String, String> body) {
        searchStockService.logKeyword(body.get("keyword"));
        return ResponseUtil.buildSuccessResponse(SuccessCode.LOG_KEYWORD_SUCCESS);
    }

}
