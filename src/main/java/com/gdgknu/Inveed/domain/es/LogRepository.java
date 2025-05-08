package com.gdgknu.Inveed.domain.es;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface LogRepository extends ElasticsearchRepository<Log, Long> {
}
