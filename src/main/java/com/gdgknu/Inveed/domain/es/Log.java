package com.gdgknu.Inveed.domain.es;

import jakarta.persistence.Id;
import lombok.Getter;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@Document(indexName = "logstash-*")
public class Log {
    @Id
    private String id;
    private String message;
    private String timestamp;

}
