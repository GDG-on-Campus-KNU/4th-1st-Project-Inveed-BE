package com.gdgknu.Inveed.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessResponse<T> {
    private final HttpStatus status;
    private final String message;
    private final T data;
    private final LocalDateTime time;

    public static <T> ResponseEntity<SuccessResponse<T>> toResponseEntity(HttpStatus status, String message, T data) {
        return ResponseEntity.status(status)
                .body(SuccessResponse.<T>builder()
                        .status(status)
                        .message(message)
                        .data(data)
                        .time(LocalDateTime.now())
                        .build());
    }

    public static ResponseEntity<SuccessResponse<Object>> toResponseEntity(HttpStatus status, String message) {
        return ResponseEntity.status(status)
                .body(SuccessResponse.builder()
                        .status(status)
                        .message(message)
                        .time(LocalDateTime.now())
                        .build());
    }

    public static ResponseEntity<Resource> toResponseEntity(HttpStatus status, Resource resource) {
        String contentDisposition = "attachment; filename=\"" + resource.getFilename() + "\"";
        return ResponseEntity.status(status)
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .contentType(MediaType.parseMediaType("image/svg+xml"))
                .body(resource);
    }
}
