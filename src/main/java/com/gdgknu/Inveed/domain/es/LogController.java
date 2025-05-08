package com.gdgknu.Inveed.domain.es;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/logs")
public class LogController {

    private final LogService logService;

    @GetMapping("/all")
    public Iterable<Log> getAllLogs() {
        return logService.findAllLogs();
    }

}
