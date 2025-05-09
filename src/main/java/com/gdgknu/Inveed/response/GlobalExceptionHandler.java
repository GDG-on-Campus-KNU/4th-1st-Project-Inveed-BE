package com.gdgknu.Inveed.response;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<ErrorResponse> handlerException(Exception ex, WebRequest request) throws Exception {
        // request.getDescription(false) returns String like "uri=/...".
        String description = request.getDescription(false);
        if (description.contains("/v3/api-docs") || description.contains("/swagger-ui")) {
            // If the path is related to Swagger, throw the exception as it is so that Springdoc or the default error handler can handle it.
            throw ex;
        }

        System.out.println("GlobalExceptionHandler: " + ex.getMessage());

        return ErrorResponse.toResponseEntity(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    // Custom
    @ExceptionHandler(value =  {CustomException.class})
    protected ResponseEntity<ErrorResponse> handlerCustomException(CustomException e) {
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }

    // Method Argument Not Valid Exception
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handlerValidationError(MethodArgumentNotValidException e) {
        if(e.getBindingResult().getFieldError() == null) {
            return ErrorResponse.toResponseEntity(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        return ErrorResponse.toResponseEntity(ErrorCode.REQUEST_FORMAT_ERROR, e.getBindingResult().getFieldError().getDefaultMessage());
    }

    // No Handler Found
    @ExceptionHandler(value =  {NoHandlerFoundException.class})
    protected ResponseEntity<ErrorResponse> handlerNoHandlerFoundException(NoHandlerFoundException e) {
        return ErrorResponse.toResponseEntity(ErrorCode.HANDLER_NOT_FOUND, e.getMessage());
    }
}