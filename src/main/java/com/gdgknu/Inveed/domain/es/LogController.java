package com.gdgknu.Inveed.domain.es;

import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;
import java.util.logging.Logger;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/logs")
public class LogController {

    private final LogService logService;
    private static final Logger logger = (Logger.getLogger(LogController.class.getName()));

    @GetMapping("/all")
    public Iterable<Log> getAllLogs() {
        return logService.findAllLogs();
    }

    @PostMapping("/search")
    public ResponseEntity<String> logSearchKeyword(@RequestBody Map<String, String> body) {
        String keyword = body.get("keyword");
        if (keyword == null || keyword.isBlank()) {
            return ResponseEntity.badRequest().body("Missing 'keyword'");
        }

        logger.info(keyword);
        return ResponseEntity.ok("Logged keyword: " + keyword);
    }

}
