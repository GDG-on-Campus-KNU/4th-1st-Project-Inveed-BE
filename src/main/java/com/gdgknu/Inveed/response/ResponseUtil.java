package com.gdgknu.Inveed.response;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {
    public static <T> ResponseEntity<SuccessResponse<T>> buildSuccessResponse(SuccessCode successCode, T data) {
        return SuccessResponse.toResponseEntity(successCode.getStatus(), successCode.getMessage(), data);
    }

    public static ResponseEntity<SuccessResponse<Object>> buildSuccessResponse(SuccessCode successCode) {
        return SuccessResponse.toResponseEntity(successCode.getStatus(), successCode.getMessage());
    }

    public static ResponseEntity<Resource> buildSuccessResourceResponse(SuccessCode successCode, Resource resource) {
        return SuccessResponse.toResponseEntity(successCode.getStatus(), resource);
    }
}