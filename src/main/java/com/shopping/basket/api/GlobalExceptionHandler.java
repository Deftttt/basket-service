package com.shopping.basket.api;

import com.shopping.basket.domain.exception.BasketAccessDeniedException;
import com.shopping.basket.domain.exception.BasketNotFoundException;
import com.shopping.basket.domain.exception.BasketNotOpenException;
import com.shopping.basket.domain.exception.InsufficientProductQuantityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BasketNotFoundException.class)
    public ResponseEntity<Object> handleBasketNotFoundException(BasketNotFoundException ex) {
        return createErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(BasketAccessDeniedException.class)
    public ResponseEntity<Object> handleBasketAccessDeniedException(BasketAccessDeniedException ex) {
        return createErrorResponse(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    @ExceptionHandler(BasketNotOpenException.class)
    public ResponseEntity<Object> handleBasketNotOpenException(BasketNotOpenException ex) {
        return createErrorResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(InsufficientProductQuantityException.class)
    public ResponseEntity<Object> handleInsufficientProductQuantityException(InsufficientProductQuantityException ex) {
        return createErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        return createErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    private ResponseEntity<Object> createErrorResponse(HttpStatus status, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", Instant.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        return new ResponseEntity<>(body, status);
    }
} 