package com.gdgknu.Inveed.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private final HttpStatus status;
    private final String title;
    private final String message;
    private final String error;
    private final LocalDateTime time;

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getStatus())
                .body(ErrorResponse.builder()
                        .status(errorCode.getStatus())
                        .title(errorCode.name())
                        .message(errorCode.getMessage())
                        .error(errorCode.getStatus().getReasonPhrase())
                        .time(LocalDateTime.now())
                        .build());
    }

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode, String message) {
        return ResponseEntity.status(errorCode.getStatus())
                .body(ErrorResponse.builder()
                        .status(errorCode.getStatus())
                        .title(errorCode.name())
                        .message(message)
                        .error(errorCode.getStatus().getReasonPhrase())
                        .time(LocalDateTime.now())
                        .build());
    }

}
