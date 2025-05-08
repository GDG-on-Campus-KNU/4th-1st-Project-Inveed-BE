package com.gdgknu.Inveed.domain.es;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogService {

    private final LogRepository logRepository;

    public Iterable<Log> findAllLogs() {
        return logRepository.findAll();
    }
}
